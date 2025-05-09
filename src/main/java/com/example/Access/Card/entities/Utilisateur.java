 package com.example.Access.Card.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
public class Utilisateur implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean actif = false;


    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Votre mail est invalide")
    private String email;


    private String nom;
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String mdp;

    private boolean active = false;

    private EnumRole role;

    public Utilisateur(Long id, boolean actif, String email, String nom, String mdp, boolean active, EnumRole role) {
        this.id = id;
        this.actif = actif;
        this.email = email;
        this.nom = nom;
        this.mdp = mdp;
        this.active = active;
        this.role = role;
    }

    public Utilisateur(){}

    public static void setUsername(String username) {
    }

    public static void setPassword(String encode) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public EnumRole getRole() {
        return role;
    }

    public void setRole(EnumRole role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() { return this.mdp; }

    @Override
    public String getUsername() { return this.email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return this.active; }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }




    public boolean getEligible() {
        return getEligible();
    }


    public boolean isGardien() {
        return isGardien();
    }


}
