package com.example.etron.serviceImpl;

import com.example.etron.JWT.JwtFilter;
import com.example.etron.POJO.Forfait;
import com.example.etron.constents.EtronConstants;
import com.example.etron.dao.ForfaitDAO;
import com.example.etron.service.ForfaitService;
import com.example.etron.utils.EtronUtils;
import com.example.etron.wrapper.ForfaitWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ForfaitServiceImpl implements ForfaitService {

    @Autowired
    ForfaitDAO forfaitDAO;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewForfait(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if(validateForfaitMap(requestMap, false)){
                    forfaitDAO.save(getForfaitFromMap(requestMap, false));
                    return EtronUtils.getResponseEntity("Forfait Ajouté avec succes", HttpStatus.OK);
                    
                }
                return EtronUtils.getResponseEntity(EtronConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

            }else
                return EtronUtils.getResponseEntity(EtronConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }catch(Exception ex){

        }
        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateForfaitMap(Map<String, String> requestMap, boolean validateId) {
        //validation de champs nom
        if(requestMap.containsKey("nom")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }
            else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private Forfait getForfaitFromMap(Map<String, String> requestMap, boolean isAdd) {

        Forfait forfait = new Forfait();
        if(isAdd){
            forfait.setId(Integer.parseInt(requestMap.get("id")));
        }else{
            forfait.setStatus("true");
        }

        forfait.setNom((requestMap.get("nom")));
        forfait.setDescription(requestMap.get("description"));
        forfait.setPrix(Integer.parseInt(requestMap.get("prix")));
        return forfait;

    }

    @Override
    public ResponseEntity<List<ForfaitWrapper>> getAllForfait() {
        try{
            return new ResponseEntity<>(forfaitDAO.getAllForfait(), HttpStatus.OK);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateForfait(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if(validateForfaitMap(requestMap, true)){
                    Optional<Forfait> optional = forfaitDAO.findById(Integer.parseInt(requestMap.get("id")));

                    //Verification si le forfait id exist
                    if(!optional.isEmpty()){
                        Forfait forfait = getForfaitFromMap(requestMap, true);
                        forfait.setStatus(optional.get().getStatus());
                        forfaitDAO.save(forfait);

                        return EtronUtils.getResponseEntity("Forfait Modifié avec Success", HttpStatus.OK);

                    }else{
                        return EtronUtils.getResponseEntity("Forfait id n'exist pas", HttpStatus.OK);
                    }

                }else{
                    return EtronUtils.getResponseEntity(EtronConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }

            }else{
                return EtronUtils.getResponseEntity(EtronConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteForfait(Integer id) {
        try{
            if(jwtFilter.isAdmin()){
                //find if the id exist or not
                Optional optional = forfaitDAO.findById(id);
                if(!optional.isEmpty()){
                    forfaitDAO.deleteById(id);
                    return EtronUtils.getResponseEntity("Forfait supprimé avec succes", HttpStatus.OK);

                }
                return EtronUtils.getResponseEntity("Forfait id n'existe pas.", HttpStatus.OK);

            }else{
                return EtronUtils.getResponseEntity(EtronConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                Optional optional = forfaitDAO.findById(Integer.parseInt((requestMap.get("id"))));
                if(!optional.isEmpty()){
                    forfaitDAO.updateForfaitStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return EtronUtils.getResponseEntity("Forfait status modifie avec succes", HttpStatus.OK);

                }
                return EtronUtils.getResponseEntity("Forfait id n'existe pas", HttpStatus.OK);

            }else{
                return EtronUtils.getResponseEntity(EtronConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ForfaitWrapper> getForfaitById(Integer id) {
        try{
            return new ResponseEntity<>(forfaitDAO.getForfaitById(id), HttpStatus.OK);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ForfaitWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
