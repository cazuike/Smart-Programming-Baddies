package com.smartprogrammingbaddies;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@Service
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

    @GetMapping("/verifyApiKey")
    public CompletableFuture<ResponseEntity<?>> verifyApiKey(@RequestParam("apiKey") String apiKey) {
        return setupDB.verifyClient(apiKey).thenApply(verified -> {
            if (verified) {
                return new ResponseEntity<>("Successfully verified API key", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("API key not found in DB.", HttpStatus.NOT_FOUND);
            }
        });
    }

    @DeleteMapping("/removeApiKey")
    public ResponseEntity<?> removeApiKey(@RequestParam("apiKey") String apiKey) {
        try {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clients/" + apiKey);
            ApiFuture<Void> future = ref.removeValueAsync();
            future.get();

            return new ResponseEntity<>("Successfully removed API Key", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to remove API key", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
