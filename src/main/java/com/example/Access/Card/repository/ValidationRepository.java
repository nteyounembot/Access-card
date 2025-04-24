package com.example.Access.Card.repository;

import com.example.Access.Card.entities.Validation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidationRepository extends CrudRepository <Validation,Integer>{
    Optional<Object> findByCode(String code);
}
