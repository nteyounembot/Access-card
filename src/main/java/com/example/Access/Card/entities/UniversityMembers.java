package com.example.Access.Card.entities;

import jakarta.persistence.*;

@Table(name = "university_member")
@Entity
public class UniversityMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String cni;

    @Column(nullable = true, unique = true)
    private String name;

    @Column(nullable = true, unique = true)
    private String email;

    @Column(nullable = true)
    private String telephone;

    @Column(nullable = true)
    private String faculte;

    private String niveau;
    private String username;
    private String picture;

    @Column(unique = true)
    private String matricule;

    @OneToOne
    @JoinColumn(name = "Utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;

    @Column(nullable = true)
    private boolean eligible = false;

    @Column(nullable = true)
    private boolean gardien;

    public UniversityMembers() {}

    public UniversityMembers(Long id, String cni, String name, String email, String telephone,
                             String faculte, String niveau, String username, String picture,
                             String matricule, Utilisateur utilisateur, boolean eligible, boolean gardien) {
        this.id = id;
        this.cni = cni;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.faculte = faculte;
        this.niveau = niveau;
        this.username = username;
        this.picture = picture;
        this.matricule = matricule;
        this.utilisateur = utilisateur;
        this.eligible = eligible;
        this.gardien = gardien;
    }


    public Long getId() {
        return id;
    }

    public String getCni() {
        return cni;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getFaculte() {
        return faculte;
    }

    public String getNiveau() {
        return niveau;
    }

    public String getUsername() {
        return username;
    }

    public String getPicture() {
        return picture;
    }

    public String getMatricule() {
        return matricule;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public boolean isEligible() {
        return eligible;
    }

    public boolean isGardien() {
        return gardien;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setFaculte(String faculte) {
        this.faculte = faculte;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public void setGardien(boolean gardien) {
        this.gardien = gardien;
    }

    public Object getRole() {
        return null;
    }

    public Object getPassword() {
        return null;
    }
}
