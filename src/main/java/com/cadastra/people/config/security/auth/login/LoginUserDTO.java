package com.cadastra.people.config.security.auth.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDTO {
    private String username;
    private String password;
}
