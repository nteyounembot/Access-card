package com.example.Access.Card.repository;

import com.example.Access.Card.entities.UniversityMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityMembersRepository  extends JpaRepository<UniversityMembers, Long>,
        JpaSpecificationExecutor<UniversityMembers> {
    UniversityMembers findAllByCni(String cni);
    UniversityMembers findAllByEmail(String email);
    UniversityMembers findAllByMatricule(String matricule);

    Optional<UniversityMembers> findByUsername(String username);

    List<UniversityMembers> findByNameContainingIgnoreCaseOrCniContainingIgnoreCaseOrEmailContainingIgnoreCaseOrTelephoneContainingIgnoreCaseOrFaculteContainingIgnoreCaseOrNiveauContainingIgnoreCaseOrMatriculeContainingIgnoreCase(
            String name,
            String cni,
            String email,
            String telephone,
            String faculte,
            String niveau,
            String matricule
    );




}

