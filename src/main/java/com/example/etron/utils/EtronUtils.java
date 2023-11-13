package com.example.etron.utils;


import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class EtronUtils {

    private EtronUtils(){

    }

    public static ResponseEntity<String> getResponseEntity (String responseMessage, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}", httpStatus);

    }

    public static String getUUID(){
        Date date = new Date();
        long time = date.getTime();
        return "Abonnement-"+time;
    }
}
