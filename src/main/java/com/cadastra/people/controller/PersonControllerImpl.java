package com.cadastra.people.controller;

import com.cadastra.people.dto.PersonDTO;
import com.cadastra.people.entity.Person;
import com.cadastra.people.entity.mapper.PersonMapper;
import com.cadastra.people.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Person", description = "People management endpoint")
@RestController
@RequestMapping("api")
public class PersonControllerImpl implements PersonController{

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonMapper personMapper;

    @Override
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO, @RequestHeader("Authorization") String token) {
        Person person = personMapper.toEntity(personDTO);
        Person createdPerson = personService.createPerson(person);
        return ResponseEntity.ok(personMapper.toDTO(createdPerson));
    }

    @Override
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Integer id) {
        return personService.getPersonById(id)
                .map(person -> ResponseEntity.ok(personMapper.toDTO(person)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<PersonDTO> updatePerson(@Parameter(description = "Person id")  @PathVariable Integer id,
                                                  @RequestBody PersonDTO personDTO,
                                                  @RequestHeader("Authorization") String token) {
        Person person = personMapper.toEntity(personDTO);
        Person updatedPerson = personService.updatePerson(id, person);
        return ResponseEntity.ok(personMapper.toDTO(updatedPerson));
    }

    @Override
    public ResponseEntity<PersonDTO> updateScore(@Parameter(description = "Person id") @PathVariable Integer id,
                                                 @RequestParam int newScore,
                                                 @RequestHeader("Authorization") String token) {
        Person updatedPerson = personService.updateScore(id, newScore);
        return ResponseEntity.ok(personMapper.toDTO(updatedPerson));
    }

    @Override
    public ResponseEntity<Void> softDeletePerson(@PathVariable Integer id,
                                                 @RequestHeader("Authorization") String token) {
        personService.softDeletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public Page<PersonDTO> getPersons(
            @Parameter(description = "Name of the person to filter by", example = "John Doe") @RequestParam(required = false) String name,
            @Parameter(description = "Age of the person to filter by", example = "30") @RequestParam(required = false) Integer age,
            @Parameter(description = "CEP of the person to filter by", example = "12345-678") @RequestParam(required = false) String cep,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Person> persons = personService.getPersons(name, age, cep, pageable);
        return persons.map(personMapper::toDTO);
    }
}