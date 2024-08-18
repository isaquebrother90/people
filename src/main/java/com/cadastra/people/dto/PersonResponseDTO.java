package com.cadastra.people.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDTO {
    private String name;
    private int age;
    private AddressDTO address;
    private String phone;
    private int score;
    private String scoreDescription;
}
