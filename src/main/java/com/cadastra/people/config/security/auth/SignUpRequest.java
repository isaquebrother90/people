package com.cadastra.people.config.security.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SignUpRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String role;
    private String name;
    private int age;
    private String phone;
    private int score;
}
