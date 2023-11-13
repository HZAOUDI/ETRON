package com.example.etron.restImpl;

import com.example.etron.constents.EtronConstants;
import com.example.etron.rest.ForfaitRest;
import com.example.etron.service.ForfaitService;
import com.example.etron.utils.EtronUtils;
import com.example.etron.wrapper.ForfaitWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ForfaitRestImpl implements ForfaitRest {
    @Autowired
    ForfaitService forfaitService;

    @Override
    public ResponseEntity<String> addNewForfait(Map<String, String> requestMap) {

        try{
            return forfaitService.addNewForfait(requestMap);

        }catch(Exception ex){
            ex.printStackTrace();

        }
        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ForfaitWrapper>> getAllForfait() {
        try{
            return forfaitService.getAllForfait();

        }catch(Exception ex){
            ex.printStackTrace();

        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateForfait(Map<String, String> requestMap) {
        try{
            return forfaitService.updateForfait(requestMap);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteForfait(Integer id) {
        try{
            return forfaitService.deleteForfait(id);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            return forfaitService.updateStatus(requestMap);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ForfaitWrapper> getForfaitById(Integer id) {
        try{
            return forfaitService.getForfaitById(id);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ForfaitWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
