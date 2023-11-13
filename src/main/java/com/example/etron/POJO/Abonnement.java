package com.example.etron.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NamedQuery(name="Abonnement.getAllAbonnement", query = "select new com.example.etron.wrapper.AbonnementWrapper(a.id, a.status, a.dateDebut, a.duree, a.forfait.id, a.forfait.nom, a.voiture.id, a.voiture.matricule, a.user) from Abonnement a ")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="abonnement")
public class Abonnement  implements Serializable {

    public static final Long serialVersionUid=123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="status")
    private String status;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "duree")
    private Integer duree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "forfait_fk", nullable = false)
    private Forfait forfait;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "voiture_fk", nullable = false)
    private Voiture voiture;

    @Column(name="user")
    private String user;


}
