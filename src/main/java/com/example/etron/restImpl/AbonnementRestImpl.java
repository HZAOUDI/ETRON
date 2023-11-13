package com.example.etron.restImpl;

import com.example.etron.constents.EtronConstants;
import com.example.etron.rest.AbonnementRest;
import com.example.etron.service.AbonnementService;
import com.example.etron.utils.EtronUtils;
import com.example.etron.wrapper.AbonnementWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class AbonnementRestImpl implements AbonnementRest {

    @Autowired
    AbonnementService abonnementService;

    @Override
    public ResponseEntity<String> addNewAbonnement(Map<String, String> requestMap) {
        try{
            return abonnementService.addNewAbonnement(requestMap);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Lister les abonnement
    @Override
    public ResponseEntity<List<AbonnementWrapper>> getAllAbonnement() {
        try{
            return abonnementService.getAllAbonnement();

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> resilier(Integer id) {
       try{
           return abonnementService.resilier(id); //complete the code

       }catch (Exception ex){
           ex.printStackTrace();
       }
        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> prolonger(Integer id, Integer inputDuree) {
        try{
            return abonnementService.prolonger(id, inputDuree);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
