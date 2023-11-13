package com.example.etron.rest;

import com.example.etron.wrapper.ForfaitWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path="/forfait")

public interface ForfaitRest {

    @PostMapping(path="/add")
    ResponseEntity<String> addNewForfait(@RequestBody Map<String, String> requestMap);

    @GetMapping(path="/get")
    ResponseEntity<List<ForfaitWrapper>> getAllForfait();

    @PostMapping(path= "/update")
    ResponseEntity<String> updateForfait(@RequestBody Map<String, String> requestMap);

    @PostMapping(path="/delete/{id}")
    ResponseEntity<String > deleteForfait(@PathVariable Integer id);

    @PostMapping(path="/updateStatus")
    ResponseEntity<String> updateStatus (@RequestBody Map<String, String > requestMap);

    @GetMapping(path = "/getById/{id}")
    ResponseEntity<ForfaitWrapper> getForfaitById(@PathVariable Integer id);

}
