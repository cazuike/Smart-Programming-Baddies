package com.smartprogrammingbaddies;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class VolunteerController {

    @Autowired
    private AuthController auth;

    @PostMapping("/enrollVolunteer")
    public ResponseEntity<?> enrollVolunteer(@RequestParam("apiKey") String apiKey, @RequestParam("name") String name) {
        try {
            boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
            if (validApiKey) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clients/" + apiKey + "/volunteers");
                String volunteerID = UUID.randomUUID().toString();
                Map<String, Object> clientData = new HashMap<>();
                clientData.put("name",name);
                ApiFuture<Void> future = ref.child(volunteerID).setValueAsync(clientData);
                future.get();
                return new ResponseEntity<>("Enrolled Volunteer ID:" + volunteerID, HttpStatus.OK);
            }
            return new ResponseEntity<>("Invalid API key", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/removeVolunteer")
    public ResponseEntity<?> removeVolunteer(@RequestParam("apiKey") String apiKey,
                                             @RequestParam("volunteerID") String volunteerID) {
        try {
            boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
            if (validApiKey) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clients/" + apiKey + "/volunteers" + volunteerID);
                ApiFuture<Void> future = ref.removeValueAsync();
                future.get();
                return new ResponseEntity<>("Deleted Volunteer ID" + volunteerID, HttpStatus.OK);
            }
            return new ResponseEntity<>("Unable to verify api key.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return handleException(e);
        }
    }
    private ResponseEntity<?> handleException(Exception e) {
        System.out.println(e.toString());
        return new ResponseEntity<>("An Error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

