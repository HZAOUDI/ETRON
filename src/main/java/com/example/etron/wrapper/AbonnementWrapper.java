package com.example.etron.wrapper;

import lombok.Data;


import java.util.Date;

@Data
public class AbonnementWrapper {

    Integer id;

    String status;

    Date dateDebut;

    Integer duree;

    Integer forfaitId;

    String forfaitNom;

    Integer voitureId;

    String voitureMaticule;

    String user;

    public AbonnementWrapper(){

    }

    public AbonnementWrapper(Integer id, String status, Date dateDebut, Integer duree, Integer forfaitId, String forfaitNom ,Integer voitureId, String voitureMaticule, String user) {
        this.id = id;
        this.status = status;
        this.dateDebut = dateDebut;
        this.duree = duree;
        this.forfaitId = forfaitId;
        this.forfaitNom = forfaitNom;
        this.voitureId = voitureId;
        this.voitureMaticule = voitureMaticule;
        this.user = user;
    }
}
