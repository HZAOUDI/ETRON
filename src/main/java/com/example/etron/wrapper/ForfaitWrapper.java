package com.example.etron.wrapper;

import lombok.Data;

@Data
public class ForfaitWrapper {

    Integer id;

    String nom;

    String description;

    Integer prix;

    String status;

    public ForfaitWrapper(){

    }

    public ForfaitWrapper(Integer id, String nom, String description, Integer prix, String status) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.status = status;
    }

    public ForfaitWrapper(Integer id, String nom, String description, Integer prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }
}
