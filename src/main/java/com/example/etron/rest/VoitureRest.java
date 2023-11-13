package com.example.etron.rest;

import com.example.etron.wrapper.VoitureWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path="/voiture")
public interface VoitureRest {
    @PostMapping(path="/add")
    ResponseEntity<String> addNewVoiture(@RequestBody Map<String, String> requestMap);

    @GetMapping(path="/get")
    ResponseEntity<List<VoitureWrapper>> getAllVoiture();

    @PostMapping(path="/update")
    ResponseEntity<String> updateVoiture(@RequestBody Map<String, String> requestMap);

    @PostMapping(path="/delete {id}")
    ResponseEntity<String> deleteVoiture(@PathVariable Integer id);

    @GetMapping(path="/getByUser/{id}")
    ResponseEntity<List<VoitureWrapper>> getByUser(@PathVariable Integer id);

    @GetMapping(path="/getById/{id}")
    ResponseEntity<VoitureWrapper> getVoitureById(@PathVariable Integer id);
}
