package com.cadastra.people.repository;

import com.cadastra.people.entity.Person;
import org.springframework.data.jpa.domain.Specification;

public class PersonSpecification {
    public static Specification<Person> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Person> hasAge(Integer age) {
        return (root, query, criteriaBuilder) ->
                age == null ? null : criteriaBuilder.equal(root.get("age"), age);
    }

    public static Specification<Person> hasCep(String cep) {
        return (root, query, criteriaBuilder) ->
                cep == null ? null : criteriaBuilder.equal(root.get("address").get("cep"), cep);
    }
}
