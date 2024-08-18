package com.cadastra.people.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
        private String cep;
        private String state;
        private String city;
        private String neighborhood;
        private String street;
}