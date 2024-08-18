package com.cadastra.people.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private int age;
    private AddressDTO address;
    private String phone;
    private int score;
}
