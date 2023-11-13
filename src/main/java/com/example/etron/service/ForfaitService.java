package com.example.etron.service;

import com.example.etron.wrapper.ForfaitWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ForfaitService {

    ResponseEntity<String> addNewForfait(Map<String, String> requestMap);

    ResponseEntity<List<ForfaitWrapper>> getAllForfait();

    ResponseEntity<String > updateForfait(Map<String, String> requestMap);

    ResponseEntity<String > deleteForfait(Integer id);

    ResponseEntity<String> updateStatus (Map<String, String> requestMap);

    ResponseEntity<ForfaitWrapper> getForfaitById(Integer id);
}
