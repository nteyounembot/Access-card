package com.example.Access.Card.service;

import com.example.Access.Card.entities.Utilisateur;
import com.example.Access.Card.entities.Validation;
import com.example.Access.Card.repository.ValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

@Service
public class ValidationService {

    private final ValidationRepository validationRepository;
    private final NotificationService notificationService;
    private final Duration amountToAdd;

    public ValidationService(ValidationRepository validationRepository, NotificationService notificationService, Duration amountToAdd) {
        this.validationRepository = validationRepository;
        this.notificationService = notificationService;
        this.amountToAdd = amountToAdd;
    }

    @Autowired
    public ValidationService(ValidationRepository validationRepository, NotificationService notificationService) {
        this.validationRepository = validationRepository;
        this.notificationService = notificationService;
        this.amountToAdd = Duration.ofMinutes(10);
    }

    public Validation enregistrer(Utilisateur utilisateur) {
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);

        Instant creation = Instant.now();
        validation.setCreation(creation);

        Instant expiration = creation.plus(amountToAdd);
        validation.setExpiration(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(1_000_000);
        String code = String.format("%06d", randomInteger);
        validation.setCode(code);

        this.validationRepository.save(validation);
        this.notificationService.envoyer(validation);

        return validation;
    }

    public Validation lireEnFonctionDuCode(String code) {
        return (Validation) this.validationRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Votre code est invalide"));
    }
}


