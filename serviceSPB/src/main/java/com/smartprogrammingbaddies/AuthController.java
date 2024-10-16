package com.smartprogrammingbaddies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AuthController {

    @Autowired
    private SetupDB setupDB;

    @GetMapping("/generateApiKey")
    public ResponseEntity<?> generateApiKey() {
        try {
            String apiKey = UUID.randomUUID().toString();
            setupDB.enrollClient(apiKey);

            return new ResponseEntity<>("Be sure to save this unique API key: " + apiKey, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Failed to generate API key", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
