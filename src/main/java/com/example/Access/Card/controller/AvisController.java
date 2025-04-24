package com.example.Access.Card.controller;

import com.example.Access.Card.entities.Avis;
import com.example.Access.Card.service.AvisService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/avis")
@RestController
public class AvisController {
    private final AvisService avisService;

    public AvisController(AvisService avisService) {
        this.avisService = avisService;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void creer( @RequestBody Avis avis) {
        this.avisService.creer(avis );
    }
}
