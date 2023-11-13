package com.example.etron.restImpl;

import com.example.etron.constents.EtronConstants;
import com.example.etron.rest.VoitureRest;
import com.example.etron.service.VoitureService;
import com.example.etron.utils.EtronUtils;
import com.example.etron.wrapper.VoitureWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class VoitureRestImpl implements VoitureRest {

    @Autowired
    VoitureService voitureService;

    @Override
    public ResponseEntity<String> addNewVoiture(Map<String, String> requestMap) {
        try{
            return voitureService.addNewVoiture(requestMap);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<VoitureWrapper>> getAllVoiture() {
        try{
            return voitureService.getAllVoiture();

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateVoiture(Map<String, String> requestMap) {
        try{
            return voitureService.updateVoiture(requestMap);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteVoiture(Integer id) {
        try{
            return voitureService.deleteVoiture(id);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<VoitureWrapper>> getByUser(Integer id) {
        try{
            return voitureService.getByUser(id);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<VoitureWrapper> getVoitureById(Integer id) {
        try{
            return  voitureService.getVoitureById(id);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new VoitureWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
