package com.cadastra.people.repository;

import com.cadastra.people.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>, JpaSpecificationExecutor<Person> {
    Optional<Person> findByName(String name);
    List<Person> findByDeletedFalse();
    Optional<Person> findByIdAndDeletedFalse(Long id);
}
