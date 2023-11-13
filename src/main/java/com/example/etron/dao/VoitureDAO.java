package com.example.etron.dao;

import com.example.etron.POJO.Voiture;
import com.example.etron.wrapper.VoitureWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoitureDAO extends JpaRepository<Voiture, Integer> {
    List<VoitureWrapper> getAllVoiture();


    List<VoitureWrapper> getVoitureByUser(@Param("id") Integer id);

    VoitureWrapper getVoitureById(@Param("id") Integer id);
}
