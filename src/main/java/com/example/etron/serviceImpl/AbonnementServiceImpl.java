package com.example.etron.serviceImpl;

import com.example.etron.JWT.JwtFilter;
import com.example.etron.POJO.Abonnement;
import com.example.etron.POJO.Forfait;
import com.example.etron.POJO.Voiture;
import com.example.etron.constents.EtronConstants;
import com.example.etron.dao.AbonnementDAO;
import com.example.etron.dao.VoitureDAO;
import com.example.etron.service.AbonnementService;
import com.example.etron.utils.EtronUtils;
import com.example.etron.wrapper.AbonnementWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AbonnementServiceImpl implements AbonnementService {

    @Autowired
    AbonnementDAO abonnementDAO;

    @Autowired
    VoitureDAO voitureDAO;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewAbonnement(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isUser()) {
                if (validateAbonnementMap(requestMap, false)) {
                    int voitureId = Integer.parseInt(requestMap.get("voitureId"));
                    String currentUserEmail = jwtFilter.getCurrentUser(); // Get the current user's email

                    // Fetch the Voiture entity
                    Optional<Voiture> optionalVoiture = voitureDAO.findById(voitureId);
                    if (optionalVoiture.isPresent()) {
                        Voiture voiture = optionalVoiture.get();
                        String voitureUserEmail = voiture.getUser().getEmail(); // Get the email of the user associated with Voiture

                        // Log the values for debugging
                        System.out.println("Current User Email: " + currentUserEmail);
                        System.out.println("Voiture User Email: " + voitureUserEmail);

                        // Check if the emails match
                        if (currentUserEmail.equals(voitureUserEmail)) {
                            Abonnement newAbonnement = getAbonnementFromMap(requestMap, false);

                            if (!isAbonnementRedundant(newAbonnement)) {
                                abonnementDAO.save(newAbonnement);
                                return EtronUtils.getResponseEntity("Abonnement ajouté avec succès", HttpStatus.OK);
                            } else {
                                return EtronUtils.getResponseEntity("Cet abonnement est redondant", HttpStatus.BAD_REQUEST);
                            }
                        } else {
                            return EtronUtils.getResponseEntity("Ce véhicule n'appartient pas à l'utilisateur actuel", HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        return EtronUtils.getResponseEntity("Véhicule introuvable", HttpStatus.NOT_FOUND);
                    }
                }
                return EtronUtils.getResponseEntity("Veuillez remplir tous les champs obligatoires", HttpStatus.BAD_REQUEST);
            } else {
                return EtronUtils.getResponseEntity(EtronConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }


        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateAbonnementMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("forfaitId") || requestMap.containsKey("voitureId")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;

            } else if (!validateId) {
                return true;
            }
        }
        return false;

    }

    private Abonnement getAbonnementFromMap(Map<String, String> requestMap, boolean isAdd) {
        Forfait forfait = new Forfait();
        forfait.setId(Integer.parseInt(requestMap.get("forfaitId")));

        Voiture voiture = new Voiture();
        voiture.setId(Integer.parseInt(requestMap.get("voitureId")));

        Abonnement abonnement = new Abonnement();
        if(isAdd){
            abonnement.setId(Integer.parseInt(requestMap.get("id")));
        }else{

        }

        abonnement.setForfait(forfait);
        abonnement.setVoiture(voiture);

        abonnement.setStatus("actif");

        String dateDebutString = requestMap.get("date_debut");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateDebut = dateFormat.parse(dateDebutString);
            abonnement.setDateDebut(dateDebut);
        } catch (ParseException e) {
            // Handle the parsing exception (e.g., log an error, provide a default date, or show an error message)
        }

        abonnement.setDuree(1);
        abonnement.setUser(jwtFilter.getCurrentUser());

        return abonnement;
    }

    //Lister tous Abonnement
    @Override
    public ResponseEntity<List<AbonnementWrapper>> getAllAbonnement() {
        try{
            return new ResponseEntity<>(abonnementDAO.getAllAbonnement(), HttpStatus.OK);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> resilier(Integer id) {
        try {
            Optional<Abonnement> optionalAbonnement = abonnementDAO.findById(id);
            if (optionalAbonnement.isPresent()) {
                Abonnement abonnement = optionalAbonnement.get();
                if ("annulé".equals(abonnement.getStatus())) {
                    return EtronUtils.getResponseEntity("Abonnement déjà annulé", HttpStatus.BAD_REQUEST);
                }
                abonnement.setStatus("annulé");
                abonnementDAO.save(abonnement);

                return EtronUtils.getResponseEntity("Abonnement annulé avec succès", HttpStatus.OK);
            } else {
                return EtronUtils.getResponseEntity("Numéro d'Abonnement n'existe pas", HttpStatus.NOT_FOUND);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> prolonger(Integer id, Integer inputDuree) {
        try {
            if (inputDuree != null && inputDuree > 0) {
                Optional<Abonnement> optionalAbonnement = abonnementDAO.findById(id);
                if (optionalAbonnement.isPresent()) {
                    Abonnement abonnement = optionalAbonnement.get();

                    // Verifier que l'abonnement est non "annuler"
                    if (!"annulé".equals(abonnement.getStatus())) {
                        // Calculate the newDuree by adding the oldDuree to the inputDuree
                        int oldDuree = abonnement.getDuree();
                        int newDuree = oldDuree + inputDuree;

                        abonnement.setDuree(newDuree);
                        abonnementDAO.save(abonnement);

                        return EtronUtils.getResponseEntity("Abonnement prolongé avec succès. Nouvelle durée : " + newDuree + " mois", HttpStatus.OK);
                    } else {
                        return EtronUtils.getResponseEntity("Impossible de prolonger un Abonnement annulé", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return EtronUtils.getResponseEntity("Numéro d'Abonnement n'existe pas", HttpStatus.NOT_FOUND);
                }
            } else {
                return EtronUtils.getResponseEntity("La durée de prolongation doit être un entier positif", HttpStatus.BAD_REQUEST);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private boolean isAbonnementRedundant(Abonnement newAbonnement) {
        List<Abonnement> existingAbonnements = abonnementDAO.findByVoitureAndForfaitAndUser(
                newAbonnement.getVoiture(), newAbonnement.getForfait(), newAbonnement.getUser());

        return !existingAbonnements.isEmpty();
    }


}
