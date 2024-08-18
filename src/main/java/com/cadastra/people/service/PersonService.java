package com.cadastra.people.service;

import com.cadastra.people.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PersonService {
    Person createPerson(Person person);
    Optional<Person> getPersonById(Integer id);
    Person updatePerson(Integer id, Person person);
    Person updateScore(Integer id, int newScore);
    void softDeletePerson(Integer id);
    Page<Person> getPersons(String name, Integer age, String cep, Pageable pageable);
}
