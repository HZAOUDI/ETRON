package com.example.etron.wrapper;

import lombok.Data;

@Data
public class VoitureWrapper {

    private Integer id;

    private String matricule;

    private String marque;

    private String modele;


    private Integer userId;

    private String userNom;

    private Integer batterie;
    public VoitureWrapper(){

    }

    public VoitureWrapper(Integer id, String matricule, String marque, String modele, Integer userId, String userNom, Integer batterie) {
        this.id = id;
        this.matricule = matricule;
        this.marque = marque;
        this.modele = modele;
        this.userId = userId;
        this.userNom = userNom;
        this.batterie = batterie;
    }

    public VoitureWrapper(Integer id, String matricule) {
        this.id = id;
        this.matricule = matricule;
    }

    public VoitureWrapper(Integer id, String matricule, String marque, String modele, Integer batterie) {
        this.id = id;
        this.matricule = matricule;
        this.marque = marque;
        this.modele = modele;
        this.batterie = batterie;
    }
}
