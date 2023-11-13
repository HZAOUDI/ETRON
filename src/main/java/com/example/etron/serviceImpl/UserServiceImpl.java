package com.example.etron.serviceImpl;

import com.example.etron.JWT.CustomerUsersDetailsService;
import com.example.etron.JWT.JwtFilter;
import com.example.etron.JWT.JwtUtil;
import com.example.etron.POJO.User;
import com.example.etron.constents.EtronConstants;
import com.example.etron.dao.UserDAO;
import com.example.etron.service.UserService;
import com.example.etron.utils.EmailUtils;
import com.example.etron.utils.EtronUtils;
import com.example.etron.wrapper.UserWrapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);

        try{
            if(validateSignUpMap(requestMap)){
                User user = userDAO.findByEmailId(requestMap.get("email"));
                if(Objects.isNull(user)){
                    userDAO.save(getUserFromMap(requestMap));
                    return EtronUtils.getResponseEntity("Inscription avec success", HttpStatus.OK);

                }else{
                    return EtronUtils.getResponseEntity("Email exist déjà", HttpStatus.BAD_REQUEST);
                }

            }else{
                return EtronUtils.getResponseEntity(EtronConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch(Exception ex){
            ex.printStackTrace();
            return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("nom") && requestMap.containsKey("prenom") && requestMap.containsKey("telephone")
                && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            String email = requestMap.get("email");

            if (email.contains("@")) {
                return true;
            }

        }
        return false;
    }


    private User getUserFromMap(Map<String,String> requestMap){
        User user = new User();
        user.setNom(requestMap.get("nom"));
        user.setPrenom(requestMap.get("prenom"));
        user.setTelephone(requestMap.get("telephone"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("true");
        user.setRole("user");

        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );

            if(auth.isAuthenticated()){
                if(customerUsersDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+

                            jwtUtil.generateToken(customerUsersDetailsService.getUserDetails().getEmail(),
                                    customerUsersDetailsService.getUserDetails().getRole()) +  "\"}",
                     HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval."+"\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }

        }catch(Exception ex){
            log.error("{}", ex);

        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials"+"\"}",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try{
            if(jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDAO.getAllUser(), HttpStatus.OK);


            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                //if user exist in database
                Optional<User> optional = userDAO.findById(Integer.parseInt(requestMap.get("id")));
                if(!optional.isEmpty()){
                    userDAO.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDAO.getAllAdmin());
                    return EtronUtils.getResponseEntity("User status est modifié avec succeess", HttpStatus.OK);

                }else{
                    return EtronUtils.getResponseEntity("User id n'existe pas", HttpStatus.OK);
                }

            }else{
                return EtronUtils.getResponseEntity(EtronConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if(status!=null && status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Compte Approuvé", "USER:- "+user+" \n est approuvé par \nADMIN:-" + jwtFilter.getCurrentUser(), allAdmin);
        }else{
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Compte Non Approuvé", "USER:- "+user+" \n est rejeté par \nADMIN:-" + jwtFilter.getCurrentUser(), allAdmin);



        }
    }

    @Override
    public ResponseEntity<String> checkToken() {

        return EtronUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            User userObj = userDAO.findByEmail(jwtFilter.getCurrentUser());
            if(!userObj.equals(null)){
                if(userObj.getPassword().equals(requestMap.get("oldPassword"))){
                    userObj.setPassword(requestMap.get("newPassword"));
                    userDAO.save(userObj);
                    return EtronUtils.getResponseEntity("Mot de passe modifié avec succes", HttpStatus.OK);

                }
                return EtronUtils.getResponseEntity("Incorrect ancien mot de passe", HttpStatus.BAD_REQUEST);
            }
            return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try{
            User user = userDAO.findByEmail(requestMap.get("email"));
            if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail()))
                emailUtils.forgotMail(user.getEmail(), "Credentiels by ETRON AUDI", user.getPassword());

                return EtronUtils.getResponseEntity("Vérifier votre email inputs.", HttpStatus.OK);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return EtronUtils.getResponseEntity(EtronConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
