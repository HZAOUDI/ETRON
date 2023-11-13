package com.example.etron.serviceImpl;

import com.example.etron.JWT.JwtFilter;
import com.example.etron.POJO.User;
import com.example.etron.POJO.Voiture;
import com.example.etron.constents.EtronConstants;
import com.example.etron.dao.VoitureDAO;
import com.example.etron.service.VoitureService;
import com.example.etron.utils.EtronUtils;
import com.example.etron.wrapper.VoitureWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VoitureServiceImpl implements VoitureService {

    @Autowired
    VoitureDAO voitureDAO;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewVoiture(Map<String, String> requestMap) {

        try{
            if(jwtFilter.isUser()){
                if(validateVoitureMap(requestMap, false)){
                    voitureDAO.save(getVoitureFromMap(requestMap, false));
                    return EtronUtils.getResponseEntity("Voiture Ajouté avec Suceess", HttpStatus.OK);

                    
                }
                return EtronUtils.getResponseEntity(EtronConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
            else{
                return EtronUtils.getResponseEntity(EtronConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateVoitureMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("matricule")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }
            else if (!validateId){
                return true;
            }
        }
        return false;
    }

    private Voiture getVoitureFromMap(Map<String, String> requestMap, boolean isAdd) {
        User user = new User();
        user.setId(Integer.parseInt(requestMap.get("userId")));

        Voiture voiture = new Voiture();

        if(isAdd){
            voiture.setId(Integer.parseInt(requestMap.get("id")));
        }else{

        }
        voiture.setUser(user);
        voiture.setMatricule(requestMap.get("matricule"));
        voiture.setMarque(requestMap.get("marque"));
        voiture.setModele(requestMap.get("modele"));
        voiture.setBatterie(Integer.parseInt(requestMap.get("batterie")));

        return voiture;
    }


    @Override
    public ResponseEntity<List<VoitureWrapper>> getAllVoiture() {
        try{
            return new ResponseEntity<>(voitureDAO.getAllVoiture(), HttpStatus.OK);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateVoiture(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if(validateVoitureMap(requestMap, true)){
                    Optional<Voiture> optional = voitureDAO.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        Voiture voiture = getVoitureFromMap(requestMap, true);

                        voitureDAO.save(voiture);
                        return EtronUtils.getResponseEntity("Voiture Modifiee avec Succes", HttpStatus.OK);

                    }else{
                        return EtronUtils.getResponseEntity("Voiture id n'existe pas", HttpStatus.OK);
                    }


                }else{
                    return EtronUtils.getResponseEntity(EtronConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            }else{ return
                EtronUtils.getResponseEntity(EtronConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }


        }catch(Exception ex){
            ex.printStackTrace();
        }

        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteVoiture(Integer id) {

        try{
            if(jwtFilter.isAdmin()){
                Optional optional = voitureDAO.findById(id);
                if(!optional.isEmpty()){
                    voitureDAO.deleteById(id);
                    return EtronUtils.getResponseEntity("Voiture Supprimee avec Success", HttpStatus.OK);

                }else{
                    return EtronUtils.getResponseEntity("Voiture id n'existe pas", HttpStatus.OK);
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
    public ResponseEntity<List<VoitureWrapper>> getByUser(Integer id) {

        try{
            return new ResponseEntity<>(voitureDAO.getVoitureByUser(id), HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return  new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<VoitureWrapper> getVoitureById(Integer id) {
        try{
            return new ResponseEntity<>(voitureDAO.getVoitureById(id), HttpStatus.OK) ;

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new VoitureWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
