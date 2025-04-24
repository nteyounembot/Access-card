package com.example.Access.Card.service;

import com.example.Access.Card.DTO.UniverstyMembersRequestDTO;
import com.example.Access.Card.entities.EnumRole;
import com.example.Access.Card.entities.Validation;
import com.example.Access.Card.entities.Utilisateur;
import com.example.Access.Card.repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
public class UtilisateurService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UtilisateurRepository utilisateurRepository;
    private final ValidationService validationService;

    public UtilisateurService(PasswordEncoder passwordEncoder, UtilisateurRepository utilisateurRepository, ValidationService validationService) {
        this.passwordEncoder = passwordEncoder;
        this.utilisateurRepository = utilisateurRepository;
        this.validationService = validationService;
    }

    public void inscription(Utilisateur utilisateur) {
        String email = String.valueOf(utilisateur.getEmail());

        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new RuntimeException("Votre mail est invalide");
        }

        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(email);
        if (utilisateurOptional.isPresent()) {
            throw new RuntimeException("Ce mail est déjà utilisé");
        }

        if (utilisateur.getMdp() == null || utilisateur.getMdp().isEmpty()) {
            throw new RuntimeException("Le mot de passe est obligatoire");
        }

        String mdpCrypte = this.passwordEncoder.encode(utilisateur.getMdp());
        utilisateur.setMdp(mdpCrypte);

        utilisateur.setRole(EnumRole.STAFF);
        utilisateur.setActive(false);

        final Utilisateur save = this.utilisateurRepository.save(utilisateur);
        this.validationService.enregistrer(save);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
    }

    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));

        if (Instant.now().isAfter(validation.getExpiration())) {
            throw new RuntimeException("Votre code a expiré");
        }

        Utilisateur utilisateur = this.utilisateurRepository
                .findById(validation.getUtilisateur().getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));

        utilisateur.setActive(true);
        utilisateurRepository.save(utilisateur);
    }



}
