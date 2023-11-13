package com.example.etron.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Forfait.getAllForfait", query = "select new com.example.etron.wrapper.ForfaitWrapper(f.id, f.nom, f.description, f.prix, f.status) from Forfait f")

@NamedQuery(name = "Forfait.updateForfaitStatus", query = "update Forfait f set f.status=:status where f.id=:id")

@NamedQuery(name = "Forfait.getForfaitById", query="select new com.example.etron.wrapper.ForfaitWrapper(f.id, f.nom, f.description, f.prix) from Forfait f where f.id=:id ")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "forfait")
public class Forfait implements Serializable {
    public static final long serialVersionUid = 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="nom")
    private String nom;

    @Column(name="description")
    private String description;

    @Column(name="prix")
    private Integer prix;

    @Column(name="status")
    private String status;


}
