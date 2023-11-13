package com.example.etron.serviceImpl;

import com.example.etron.dao.AbonnementDAO;
import com.example.etron.dao.ForfaitDAO;
import com.example.etron.dao.VoitureDAO;
import com.example.etron.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    ForfaitDAO forfaitDAO;

    @Autowired
    VoitureDAO voitureDAO;

    @Autowired
    AbonnementDAO abonnementDAO;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("forfait", forfaitDAO.count());
        map.put("voiture", voitureDAO.count());
        map.put("abonnement", abonnementDAO.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
