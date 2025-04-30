package com.example.Access.Card.service;

import com.example.Access.Card.DTO.UniversityMembersResponseDTO;
import com.example.Access.Card.DTO.UniverstyMembersRequestDTO;
import com.example.Access.Card.Mapper.UniversityMembersMapper;
import com.example.Access.Card.Specification.UniversityMembersSpecification;
import com.example.Access.Card.entities.UniversityMembers;
import com.example.Access.Card.entities.Utilisateur;
import com.example.Access.Card.repository.UniversityMembersRepository;
import com.example.Access.Card.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityMembersService {
    private final UniversityMembersRepository universityMembersRepository;
    private final UtilisateurRepository utilisateurRepository;
    private  final PasswordEncoder passwordEncoder;
    private Utilisateur Utilisateur;
    private UniversityMembers member;
    private Object universityMembersMapper;


    public UniversityMembersService(UniversityMembersRepository universityMembersRepository, UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.universityMembersRepository = universityMembersRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public UniversityMembersResponseDTO createMember(UniverstyMembersRequestDTO dto){
        System.out.println("les dto"+dto);
        Utilisateur user = new Utilisateur();
        user.setNom(dto.name());
        user.setEmail(dto.email());
        user.setRole(dto.role());

        Utilisateur userSave = utilisateurRepository.save(user);

        UniversityMembers member = new UniversityMembers();
        member.setCni(dto.cni());
        member.setName(dto.name());
        member.setEmail(dto.email());
        member.setTelephone(dto.telephone());
        member.setFaculte(dto.faculte());
        member.setNiveau(dto.niveau());
        member.setMatricule(dto.matricule());
        member.setUtilisateur(user);
        member.setEligible(dto.eligible());
        UniversityMembers memberSave = universityMembersRepository.save(member);
        return new UniversityMembersMapper().apply(memberSave);


    }
    @Transactional
    public UniversityMembersResponseDTO updateMember(Long id,UniversityMembersResponseDTO dto) {
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

        member = universityMembersRepository.save(member);

        return new UniversityMembersMapper().apply(member);
    }

   public UniversityMembersResponseDTO getMemberById(Long id) {
        UniversityMembers member = universityMembersRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Membre non trouvé avec l'id: " + id));

      return new UniversityMembersMapper().apply(member);


    }
    public List<UniversityMembersResponseDTO> searchMembers(
            String cni, String name, String email, String telephone,
            String faculte, String niveau, String matricule, Boolean eligible) {

        Specification<UniversityMembers> spec = Specification
                .where(UniversityMembersSpecification.hasCni(cni))
                .and(UniversityMembersSpecification.hasName(name))
             //   .and(UniversityMembersSpecification.hasEmail(email))
                .and(UniversityMembersSpecification.hasTelephone(telephone))
                .and(UniversityMembersSpecification.hasFaculte(faculte))
                .and(UniversityMembersSpecification.hasNiveau(niveau))
                .and(UniversityMembersSpecification.hasMatricule(matricule))
                .and(UniversityMembersSpecification.isEligible(eligible));

        List<UniversityMembers> members = universityMembersRepository.findAll(spec);

        UniversityMembersMapper mapper = new UniversityMembersMapper();

        return members.stream()
                .map(mapper::apply)
                .collect(Collectors.toList());
    }

    @Transactional
    public UniversityMembersResponseDTO deleteMember(Long id) {
        UniversityMembers member = universityMembersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé avec l'id: " + id));

        UniversityMembersResponseDTO dto = new UniversityMembersMapper().apply(member);

        Utilisateur utilisateur = member.getUtilisateur();
        if (utilisateur != null) {
            utilisateurRepository.delete(utilisateur);
        }

        universityMembersRepository.delete(member);

        return dto;
    }
    public UniversityMembersResponseDTO toggleGardien(Long id) {
        UniversityMembers member = universityMembersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));

        member.setGardien(!member.getUtilisateur().getGardien());

        UniversityMembers updated = universityMembersRepository.save(member);

        return new UniversityMembersMapper().apply(member);

    }
    public UniversityMembersResponseDTO toggleEligible(Long id) {
        UniversityMembers member = universityMembersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));

        member.setEligible(!member.getUtilisateur().getEligible());

        UniversityMembers updated = universityMembersRepository.save(member);

        return new UniversityMembersMapper().apply(member);

    }
    }








