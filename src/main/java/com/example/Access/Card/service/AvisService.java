package com.example.Access.Card.service;

import com.example.Access.Card.entities.Avis;
import com.example.Access.Card.repository.AvisRepository;
import org.springframework.stereotype.Service;



@Service
public class AvisService {

    private final AvisRepository avisRepository;



    public AvisService(AvisRepository avisRepository) {
        this.avisRepository = avisRepository;
    }

    public void creer(Avis avis) {
        this.avisRepository.save(avis);
    }
}