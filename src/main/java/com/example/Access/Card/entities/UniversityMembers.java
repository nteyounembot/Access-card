package com.example.Access.Card.entities;

import jakarta.persistence.*;

@Table(name = "university_member")
@Entity
public class UniversityMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true, unique = true)
    private String cni;
    @Column (nullable = true)
    private String name;
    @Column (nullable = true, unique = true)
    private String email;
    @Column (nullable = true, unique = true)
    private String telephone;
    @Column (nullable = true)
    private String faculte;
    private  String niveau;

    private String username;
    private  String picture;
    private  String matricule;
    @OneToOne
    @JoinColumn(name = " Utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;
    @Column (nullable = true)
    private  boolean eligible=false;
    @Column (nullable = true)
    private  boolean gardien;

    public UniversityMembers(Long id, String cni, String name, String email, String telephone, String faculte, String niveau, String username, String picture, String matricule, Utilisateur utilisateur, boolean eligible, boolean gardien) {
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
    public UniversityMembers(){}

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getMatricule() {
        return matricule;
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public boolean isGardien() {
        return gardien;
    }

    public void setGardien(boolean gardien) {
        this.gardien = gardien;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCNI() {
        return cni;
    }

    public void setCNI(String CNI) {
        this.cni = cni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        telephone = telephone;
    }

    public String getFaculte() {
        return faculte;
    }

    public void setFaculte(String faculte) {
        faculte = faculte;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        niveau = niveau;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        picture = picture;
    }

    public String getmatricule() {
        return matricule;
    }

     public void setMatricule(String matricule) {
        matricule = matricule;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Object getRole() {
        return null;
    }

    public String getUsername() {
        return username;
    }

    public boolean getGardien() {
        return gardien;
    }

    public Object getPassword() {
        return getPassword();
    }
}
