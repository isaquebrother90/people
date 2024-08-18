package com.cadastra.people.service.impl;

import com.cadastra.people.client.AddressRequestClient;
import com.cadastra.people.entity.Address;
import com.cadastra.people.entity.Person;
import com.cadastra.people.repository.PersonRepository;
import com.cadastra.people.repository.PersonSpecification;
import com.cadastra.people.repository.UserRepository;
import com.cadastra.people.service.AddressService;
import com.cadastra.people.service.PersonService;
import com.cadastra.people.util.ScoreCalc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressService addressService;

    @Override
    public Person createPerson(Person person) {
        Optional<Person> existingPerson = personRepository.findByName(person.getName());

        if (existingPerson.isPresent()) {
            throw new IllegalArgumentException("Usuário com esse nome já existe.");
        }
        person.setScoreDescription(ScoreCalc.classifyScore(person.getScore()));

        AddressRequestClient requestResponse = addressService.buscarEndereco(person.getAddress().getCep());

        person.setAddress(new Address(
                null,
           requestResponse.getCep(),
           requestResponse.getUf(),
           requestResponse.getLocalidade(),
           requestResponse.getBairro(),
           requestResponse.getLogradouro()
        ));

        Person personResponse = personRepository.save(person);
        return personResponse;
    }

    @Override
    public Page<Person> getPersons(String name, Integer age, String cep, Pageable pageable) {
        Specification<Person> spec = Specification.where(PersonSpecification.hasName(name))
                .and(PersonSpecification.hasAge(age))
                .and(PersonSpecification.hasCep(cep));
        return personRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<Person> getPersonById(Integer id) {
        return personRepository.findById(id);
    }

    @Override
    public Person updatePerson(Integer id, Person person) {
        Optional<Person> existingPerson = personRepository.findByName(person.getName());

        if (existingPerson.isPresent()) {
            throw new IllegalArgumentException("Usuário com esse nome já existe.");
        }
        person.setId(id);
        person.setScoreDescription(ScoreCalc.classifyScore(person.getScore()));
        Person personResponse = personRepository.save(person);
        return personResponse;
    }

    @Override
    public Person updateScore(Integer id, int newScore) {
        Optional<Person> personOpt = personRepository.findById(id);
        if (personOpt.isPresent()) {
            Person person = personOpt.get();
            person.setScore(newScore);
            return personRepository.save(person);
        }
        return null;
    }

    @Override
    public void softDeletePerson(Integer id) {
        Optional<Person> personOpt = personRepository.findById(id);
        if (personOpt.isPresent()) {
            Person person = personOpt.get();
            person.setDeleted(true);
            personRepository.save(person);
        }
    }
}
