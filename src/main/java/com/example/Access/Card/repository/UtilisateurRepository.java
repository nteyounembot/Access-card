package com.example.Access.Card.repository;

import com.example.Access.Card.entities.UniversityMembers;
import com.example.Access.Card.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {


    Optional<Utilisateur> findByEmail(String email);

}