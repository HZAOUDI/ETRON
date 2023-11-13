package com.example.etron.rest;

import com.example.etron.wrapper.AbonnementWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path="/abonnement")
public interface AbonnementRest {

    @PostMapping(path="/add")
    ResponseEntity<String> addNewAbonnement(@RequestBody Map<String, String> requestMap);

    @GetMapping(path="/get")
    ResponseEntity<List<AbonnementWrapper>> getAllAbonnement();

    @PostMapping(path = "/resilier/{id}")
    ResponseEntity<String> resilier(@PathVariable Integer id);

    @PostMapping(path = "/prolonger/{id}/{inputDuree}")
    ResponseEntity<String> prolonger(@PathVariable Integer id, @PathVariable Integer inputDuree);

}
