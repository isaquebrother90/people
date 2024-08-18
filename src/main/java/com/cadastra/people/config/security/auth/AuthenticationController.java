package com.cadastra.people.config.security.auth;

import com.cadastra.people.config.security.auth.login.LoginResponse;
import com.cadastra.people.config.security.auth.login.LoginUserDTO;
import com.cadastra.people.entity.User;
import com.cadastra.people.entity.mapper.PersonMapper;
import com.cadastra.people.config.security.JwtService;
import com.cadastra.people.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Auth management endpoint")
@RequestMapping("auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private PersonService personService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Operation(
            summary = "Register a user",
            description = "Temporary resource to create a new user, pasword and role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody SignUpRequest signUpRequest) {
        User registeredUser = authenticationService.signup(signUpRequest);

        return ResponseEntity.ok(registeredUser);
    }

    @Operation(
            summary = "Sign in",
            description = "Log in with username and password and return token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
