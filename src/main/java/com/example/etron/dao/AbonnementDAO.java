package com.example.etron.dao;

import com.example.etron.POJO.Abonnement;
import com.example.etron.POJO.Forfait;
import com.example.etron.POJO.Voiture;
import com.example.etron.wrapper.AbonnementWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface AbonnementDAO extends JpaRepository<Abonnement, Integer> {
    List<AbonnementWrapper> getAllAbonnement();

    List<Abonnement> findByVoitureAndForfaitAndUser(Voiture voiture, Forfait forfait, String user);

}
