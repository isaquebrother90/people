package com.cadastra.people.config.security.auth;

import com.cadastra.people.config.security.auth.login.LoginUserDTO;
import com.cadastra.people.entity.User;
import com.cadastra.people.repository.UserRepository;
import com.cadastra.people.util.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(SignUpRequest input) {
        Optional<User> existingPerson = userRepository.findByUsername(input.getUsername());

        if (existingPerson.isPresent()) {
            // Se a pessoa já existe, lança uma exceção ou retorna uma mensagem
            throw new IllegalArgumentException("Usuário com esse nome já existe.");
        }
        User user = new User();
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(Role.valueOf(input.getRole()));
        user.setName(input.getName());

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return userRepository.findByUsername(input.getUsername())
                .orElseThrow();
    }
}
