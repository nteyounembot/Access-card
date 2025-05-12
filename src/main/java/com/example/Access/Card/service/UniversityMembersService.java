package com.example.Access.Card.service;

import com.example.Access.Card.DTO.UniversityMembersResponseDTO;
import com.example.Access.Card.DTO.UniversityMembersSpecification;
import com.example.Access.Card.DTO.UniverstyMembersRequestDTO;
import com.example.Access.Card.Mapper.UniversityMembersMapper;
import com.example.Access.Card.entities.UniversityMembers;
import com.example.Access.Card.entities.Utilisateur;
import com.example.Access.Card.repository.UniversityMembersRepository;
import com.example.Access.Card.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UniversityMembersService {

    private final UniversityMembersRepository universityMembersRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UniversityMembersService(UniversityMembersRepository universityMembersRepository,
                                    UtilisateurRepository utilisateurRepository,
                                    PasswordEncoder passwordEncoder) {
        this.universityMembersRepository = universityMembersRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UniversityMembersResponseDTO createMember(UniverstyMembersRequestDTO dto) {
        System.out.println("Les DTO reçus: " + dto);
        Utilisateur user = null;
        Optional<Utilisateur> userExist = utilisateurRepository.findByEmail(dto.email());

        if (userExist.isPresent()) {
            user = userExist.get();
        } else {
            user = new Utilisateur();
            user.setNom(dto.name());
            user.setEmail(dto.email());
            user.setRole(dto.role());
            user = utilisateurRepository.save(user);
        }

        UniversityMembers member = new UniversityMembers();
        member.setCni(dto.cni());
        member.setName(dto.name());
        member.setEmail(dto.email());
        member.setTelephone(dto.telephone());
        member.setUtilisateur(user);  // Ici, user est bien assigné
        member.setEligible(dto.eligible());

// Gérer les champs spécifiques selon le rôle
        if (dto.role().name().equals("STUDENT") || dto.role().name().equals("TEACHER")) {
            member.setFaculte(dto.faculte() != null ? dto.faculte() : "");
            member.setNiveau(dto.niveau() != null ? dto.niveau() : "");
            member.setMatricule(dto.matricule() != null ? dto.matricule() : "");
        } else {
            member.setFaculte("");
            member.setNiveau("");
            member.setMatricule("");
        }

        UniversityMembers saved = universityMembersRepository.save(member);
        return new UniversityMembersMapper().apply(saved);

    }

    @Transactional
    public UniversityMembersResponseDTO updateMember(Long id, UniversityMembersResponseDTO dto) {
        UniversityMembers member = universityMembersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));

        Utilisateur utilisateur = member.getUtilisateur();
        utilisateur.setUsername(dto.username());
        utilisateur.setRole(dto.role());
        utilisateurRepository.save(utilisateur);

        member.setName(dto.name());
        member.setCni(dto.cni());
        member.setEmail(dto.email());
        member.setTelephone(dto.telephone());
        member.setNiveau(dto.niveau());
        member.setMatricule(dto.matricule());
        member.setEligible(dto.eligible());

        UniversityMembers updated = universityMembersRepository.save(member);
        return new UniversityMembersMapper().apply(updated);
    }

    public UniversityMembersResponseDTO getMemberById(Long id) {
        UniversityMembers member = universityMembersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé avec l'id: " + id));
        return new UniversityMembersMapper().apply(member);
    }

    public List<UniversityMembersResponseDTO> searchMembers(String query) {
        Specification<UniversityMembers> spec = UniversityMembersSpecification.matchesQuery(query);
        List<UniversityMembers> members = universityMembersRepository.findAll(spec);
        UniversityMembersMapper mapper = new UniversityMembersMapper();
        return members.stream().map(mapper::apply).collect(Collectors.toList());
    }

    @Transactional
    public UniversityMembersResponseDTO deleteMember(Long id) {
        UniversityMembers member = universityMembersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé avec l'id: " + id));

        UniversityMembersResponseDTO dto = new UniversityMembersMapper().apply(member);

        // D'abord supprimer le membre
        universityMembersRepository.delete(member);

        // Ensuite supprimer l'utilisateur (s'il n'est plus utilisé ailleurs)
        Utilisateur utilisateur = member.getUtilisateur();
        if (utilisateur != null) {
            utilisateurRepository.delete(utilisateur);
        }

        return dto;
    }


    public UniversityMembersResponseDTO toggleGardien(Long id) {

        System.out.println("oui la laide cest Nteyou nembot l'enfant de son pere");
        UniversityMembers member = universityMembersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));

        System.out.println("oui la laide cest Nteyou nembot l'enfant de son pere");
        member.setGardien(!member.isGardien());
        UniversityMembers updated = universityMembersRepository.save(member);
        System.out.println("oui la laide cest Nteyou nembot l'enfant de son pere");
        return new UniversityMembersMapper().apply(updated);
    }

    public UniversityMembersResponseDTO toggleEligible(Long id) {
        UniversityMembers member = universityMembersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));

        member.setEligible(!member.getUtilisateur().getEligible());
        UniversityMembers updated = universityMembersRepository.save(member);
        return new UniversityMembersMapper().apply(updated);
    }
    public List<UniversityMembersResponseDTO> getAllMembers() {
        return universityMembersRepository.findAll()
                .stream()
                .map(new UniversityMembersMapper()::apply)
                .collect(Collectors.toList());
    }


}








