package com.example.etron.service;

import com.example.etron.POJO.Abonnement;
import com.example.etron.POJO.Voiture;
import com.example.etron.wrapper.AbonnementWrapper;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AbonnementService {

    ResponseEntity<String> addNewAbonnement(Map<String, String> requestMap);

    ResponseEntity<List<AbonnementWrapper>> getAllAbonnement();

    ResponseEntity<String> resilier (Integer id);

    ResponseEntity<String> prolonger(Integer id, Integer inputDuree);


}
