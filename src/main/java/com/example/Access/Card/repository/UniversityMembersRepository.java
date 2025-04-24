package com.example.Access.Card.repository;

import com.example.Access.Card.entities.UniversityMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityMembersRepository  extends JpaRepository<UniversityMembers, Long>,
        JpaSpecificationExecutor<UniversityMembers> {
    UniversityMembers findAllByCni(String cni);
    UniversityMembers findAllByEmail(String email);
    UniversityMembers findAllByMatricule(String matricule);

    Optional<Object> findByUsername(String username);
}

