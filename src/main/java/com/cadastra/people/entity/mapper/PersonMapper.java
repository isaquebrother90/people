package com.cadastra.people.entity.mapper;

import com.cadastra.people.dto.AddressDTO;
import com.cadastra.people.dto.PersonDTO;
import com.cadastra.people.entity.Address;
import com.cadastra.people.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public PersonDTO toDTO(Person person) {
        AddressDTO addressDTO = new AddressDTO(
                person.getAddress().getCep(),
                person.getAddress().getState(),
                person.getAddress().getCity(),
                person.getAddress().getNeighborhood(),
                person.getAddress().getStreet()
        );

        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getAge(),
                addressDTO,
                person.getPhone(),
                person.getScore(),
                person.getScoreDescription()
        );
    }

    public Person toEntity(PersonDTO personDTO) {
        Address address = new Address();
        address.setCep(personDTO.getAddress().getCep());
        address.setState(personDTO.getAddress().getState());
        address.setCity(personDTO.getAddress().getCity());
        address.setNeighborhood(personDTO.getAddress().getNeighborhood());
        address.setStreet(personDTO.getAddress().getStreet());

        Person user = new Person();
        user.setName(personDTO.getName());
        user.setAge(personDTO.getAge());
        user.setAddress(address);
        user.setPhone(personDTO.getPhone());
        user.setScore(personDTO.getScore());
        user.setScoreDescription(personDTO.getScoreDescription());

        return user;
    }
}
