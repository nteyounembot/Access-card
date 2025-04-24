package com.example.Access.Card.repository;

import com.example.Access.Card.entities.Avis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisRepository  extends CrudRepository <Avis, Integer>{
}
