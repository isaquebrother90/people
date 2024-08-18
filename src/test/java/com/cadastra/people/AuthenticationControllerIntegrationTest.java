package com.cadastra.people;

import com.cadastra.people.config.security.JwtService;
import com.cadastra.people.config.security.auth.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    @Test
    public void testRegister() throws Exception {
        String signUpRequestJson = createSignUpRequestJson("mockuser", "mockpassword", "USER", "mockuser@example.com", "Mock", "User", "Mock User");

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUpRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("mockuser")));
    }

    @Test
    public void testAuthenticate() throws Exception {
        // Primeiro, registre um usuário
        String signUpRequestJson = createSignUpRequestJson("testuser", "password", "USER", "testuser@example.com", "Test", "User", "Test User");
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUpRequestJson))
                .andExpect(status().isOk());

        // Agora, autentique o usuário
        String loginRequestJson = createLoginRequestJson("testuser", "password");
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.expiresIn", greaterThan(0)));
    }

    private String createSignUpRequestJson(String username, String password, String role, String email, String firstName, String lastName, String name) {
        return "{ " +
                "\"username\": \"" + username + "\", " +
                "\"password\": \"" + password + "\", " +
                "\"role\": \"" + role + "\", " +
                "\"email\": \"" + email + "\", " +
                "\"firstName\": \"" + firstName + "\", " +
                "\"lastName\": \"" + lastName + "\", " +
                "\"name\": \"" + name + "\" " +
                "}";
    }

    private String createLoginRequestJson(String username, String password) {
        return "{ " +
                "\"username\": \"" + username + "\", " +
                "\"password\": \"" + password + "\" " +
                "}";
    }
}
