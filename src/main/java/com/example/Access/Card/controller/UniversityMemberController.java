package com.example.Access.Card.controller;

import com.example.Access.Card.DTO.*;
import com.example.Access.Card.entities.Utilisateur;
import com.example.Access.Card.repository.UniversityMembersRepository;
import com.example.Access.Card.repository.UtilisateurRepository;
import com.example.Access.Card.security.JwtService;
import com.example.Access.Card.service.UniversityMembersService;
import com.example.Access.Card.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class UniversityMemberController {

    private UniversityMembersRepository  universityMembersRepository;
    private  PasswordEncoder passwordEncoder;
    private  final UtilisateurRepository utilisateurRepository;
    private final UniversityMembersService universityMembersService;

    private final AuthenticationManager authenticationManager;
    private final UtilisateurService utilisateurService;
    private final JwtService jwtService;

    public UniversityMemberController(UniversityMembersRepository universityMembersRepository, PasswordEncoder passwordEncoder, UtilisateurRepository utilisateurRepository, UniversityMembersService universityMembersService, AuthenticationManager authenticationManager, UtilisateurService utilisateurService, JwtService jwtService) {
        this.universityMembersRepository = universityMembersRepository;
        this.passwordEncoder = passwordEncoder;
        this.utilisateurRepository = utilisateurRepository;
        this.universityMembersService = universityMembersService;
        this.authenticationManager = authenticationManager;
        this.utilisateurService = utilisateurService;
        this.jwtService = jwtService;
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
    @PutMapping("/toggle-eligible/{id}")
    public ResponseEntity<UniversityMembersResponseDTO> toggleEligible(@PathVariable Long id) {
        UniversityMembersResponseDTO updated = universityMembersService.toggleGardien(id);
        return ResponseEntity.ok(updated);
    }
    @PostMapping("/login-gardien")
    public ResponseEntity<?> loginGardien(@RequestBody GardienLoginRequest request) {
        Map<String, String> response = new HashMap<>();

        Utilisateur user = utilisateurRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("email  de cette utilisateur est introuvable, ou accès non autorisé"));

        user.setActive(true);
        utilisateurRepository.save(user);
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mot de passe incorrect");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );

            if (authentication.isAuthenticated()) {

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
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(401).body(response);
        }

    }
    }



