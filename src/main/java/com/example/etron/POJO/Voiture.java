package com.example.etron.POJO;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

import com.example.etron.POJO.User;

@NamedQuery(name="Voiture.getAllVoiture", query = "select new com.example.etron.wrapper.VoitureWrapper(v.id, v.matricule, v.marque, v.modele, v.user.id, v.user.nom, v.batterie) from Voiture v")

@NamedQuery(name= "Voiture.getVoitureByUser", query = "select new com.example.etron.wrapper.VoitureWrapper(v.id, v.matricule) from Voiture v where v.user.id =: id")

@NamedQuery(name="Voiture.getVoitureById", query = "select new com.example.etron.wrapper.VoitureWrapper(v.id, v.matricule, v.marque, v.modele, v.batterie) from Voiture v where v.id=: id ")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "voiture")
public class Voiture implements Serializable {

    public static final Long serialVersionUid = 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "matricule")
    private String matricule;

    @Column(name="marque")
    private String marque;

    @Column(name="modele")
    private String modele;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_fk", nullable = false)
    private User user;


    @Column(name = "batterie")
    private Integer batterie;

}
