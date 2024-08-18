package com.cadastra.people.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private Integer id;
    private String name;
    private int age;
    private AddressDTO address;
    private String phone;
    private int score;
    private String scoreDescription;
}
