package com.cadastra.people.config.security.auth.login;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    private String token;
    private long expiresIn;
}
