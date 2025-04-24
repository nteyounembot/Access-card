package com.example.Access.Card.controller;

import com.example.Access.Card.DTO.AuthentificationDTO;
import com.example.Access.Card.DTO.UniversityMembersResponseDTO;
import com.example.Access.Card.entities.Utilisateur;
import com.example.Access.Card.security.JwtService;
import com.example.Access.Card.service.UniversityMembersService;
import com.example.Access.Card.service.UtilisateurService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {

    private static final Logger log = LoggerFactory.getLogger(UtilisateurController.class);

    private final AuthenticationManager authenticationManager;
    private final UtilisateurService utilisateurService;
    private final JwtService jwtService;
    private com.example.Access.Card.service.UniversityMembersService UniversityMembersService;

    public UtilisateurController(UtilisateurService utilisateurService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.utilisateurService = utilisateurService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping(path = "/inscription", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> inscription(@Valid @RequestBody Utilisateur utilisateur) {
        log.info("Tentative d'inscription de l'utilisateur : {}", utilisateur.getEmail());
        utilisateurService.inscription(utilisateur);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Inscription réussie. Veuillez vérifier votre email pour activer le compte.");
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/activation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> activation(@RequestBody Map<String, String> activation) {
        log.info("Activation du compte avec le code : {}", activation.get("code"));
        utilisateurService.activation(activation);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Compte activé avec succès.");
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/connexion", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> connexion(@RequestBody AuthentificationDTO authenticationDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationDTO.username(),
                            authenticationDTO.password()
                    )
            );

            if (authentication.isAuthenticated()) {
                log.info("Authentification réussie pour : {}", authenticationDTO.username());
                String token = jwtService.generateToken(authentication);
                response.put("status", "success");
                response.put("token", token);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "failure");
                response.put("message", "Authentification échouée.");
                return ResponseEntity.status(401).body(response);
            }

        } catch (Exception e) {
            log.error("Échec d'authentification : {}", e.getMessage());
            response.put("status", "error");
            response.put("message", "Identifiants invalides ou compte inactif.");
            return ResponseEntity.status(401).body(response);
        }
    }


    }


