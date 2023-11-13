package com.example.etron.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {

    private Integer id;

    private String nom;

    private String prenom;

    private String telephone;

    private String email;

    private String status;


    public UserWrapper(Integer id, String nom, String prenom, String telephone, String email, String status) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.status = status;
    }
}
