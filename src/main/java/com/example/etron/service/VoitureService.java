package com.example.etron.service;

import com.example.etron.wrapper.VoitureWrapper;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.List;

public interface VoitureService {

    ResponseEntity<String> addNewVoiture(Map<String, String> requestMap);

    ResponseEntity<List<VoitureWrapper>> getAllVoiture();

    ResponseEntity<String> updateVoiture(Map<String, String> requestMap);

    ResponseEntity<String> deleteVoiture (Integer id);

    ResponseEntity<List <VoitureWrapper>> getByUser(Integer id);

    ResponseEntity<VoitureWrapper> getVoitureById(Integer id);
}
