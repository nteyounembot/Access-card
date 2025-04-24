package com.example.Access.Card.controller;

import com.example.Access.Card.DTO.*;
import com.example.Access.Card.entities.UniversityMembers;
import com.example.Access.Card.entities.Utilisateur;
import com.example.Access.Card.repository.UniversityMembersRepository;
import com.example.Access.Card.repository.UtilisateurRepository;
import com.example.Access.Card.service.UniversityMembersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class UniversityMemberController {

    private final UniversityMembersService universityMembersService;
    private final UniversityMembersRepository universityMembersRepository;
    private final PasswordEncoder passwordEncoder;

    public UniversityMemberController(
            UniversityMembersService universityMembersService,
            UniversityMembersRepository universityMembersRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.universityMembersService = universityMembersService;
        this.universityMembersRepository = universityMembersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/create")
    public ResponseEntity<UniversityMembersResponseDTO> createMember(
            @RequestBody UniverstyMembersRequestDTO dto) {
        UniversityMembersResponseDTO createdMember = universityMembersService.createMember(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMember);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UniversityMembersResponseDTO> updatemember(
            @PathVariable("id") Long id,
            @RequestBody UniversityMembersResponseDTO dto) {
        UniversityMembersResponseDTO updatedMember = universityMembersService.updateMember(id, dto);
        return ResponseEntity.ok(updatedMember);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityMembersResponseDTO> getMemberById(@PathVariable Long id) {
        UniversityMembersResponseDTO member = universityMembersService.getMemberById(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchMembers(
            @RequestParam(required = false) String cni,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telephone,
            @RequestParam(required = false) String faculte,
            @RequestParam(required = false) String niveau,
            @RequestParam(required = false) String matricule,
            @RequestParam(required = false) Boolean eligible
    ) {
        return ResponseEntity.ok(universityMembersService.searchMembers(
                cni, name, email, telephone, faculte, niveau, matricule, eligible
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UniversityMembersResponseDTO> deleteMember(@PathVariable Long id) {
        UniversityMembersResponseDTO deletedMember = universityMembersService.deleteMember(id);
        return ResponseEntity.ok(deletedMember);
    }

    @PutMapping("/toggle-gardien/{id}")
    public ResponseEntity<UniversityMembersResponseDTO> toggleGardien(@PathVariable Long id) {
        UniversityMembersResponseDTO updated = universityMembersService.toggleGardien(id);
        return ResponseEntity.ok(updated);
    }


    @PostMapping("/login-gardien")
    public ResponseEntity<?> loginGardien(@RequestBody GardienLoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        Utilisateur member = (Utilisateur) UtilisateurRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("Nom d'utilisateur introuvable"));

        if (!passwordEncoder.matches(request.password(), (String) member.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mot de passe incorrect");
        }

        if (!Boolean.TRUE.equals(member.getGardien())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Accès refusé : vous n'êtes pas autorisé en tant que gardien");
        }

        response.put("status", "success");
        response.put("message", "Connexion réussie en tant que gardien");
        response.put("username", member.getUsername());
        response.put("memberId", member.getId());

        return ResponseEntity.ok(response);
    }

    }




