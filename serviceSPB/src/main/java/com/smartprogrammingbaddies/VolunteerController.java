package com.smartprogrammingbaddies;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains the VolunteerController class.
 */
@RestController
public class VolunteerController {

  @Autowired
  private AuthController auth;

  /**
   * Enrolls a volunteer into the database.
   *
   * @param apiKey A {@code String} representing the API key.
   * @param name A {@code String} representing the volunteer's name.
   * @return A {@code ResponseEntity} A message if Volunteer was successfully enrolled
     and a HTTP 200 response or, HTTP 404 reponse if API Key was not found.
   */
  @PostMapping("/enrollVolunteer")
  public ResponseEntity<?> enrollVolunteer(@RequestParam("apiKey") String apiKey,
      @RequestParam("name") String name) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        DatabaseReference ref;
        String refString = "clients/" + apiKey + "/volunteers";
        ref = FirebaseDatabase.getInstance().getReference(refString);
        String volunteerId = UUID.randomUUID().toString();
        Map<String, Object> clientData = new HashMap<>();
        clientData.put("name", name);
        ApiFuture<Void> future = ref.child(volunteerId).setValueAsync(clientData);
        future.get();
        return new ResponseEntity<>("Enrolled Volunteer ID:" + volunteerId, HttpStatus.OK);
      }
      return new ResponseEntity<>("Invalid API key", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Removes a volunteer from the database.
   *
   * @param apiKey A {@code String} representing the API key.
   * @param volunteerId A {@code String} representing the volunteer ID.
   * @return A {@code ResponseEntity} A message if Volunteer was successfully removed
     and a HTTP 200 response or, HTTP 404 reponse if API Key was not found.
   */
  @DeleteMapping("/removeVolunteer")
  public ResponseEntity<?> removeVolunteer(@RequestParam("apiKey") String apiKey,
                                            @RequestParam("volunteerId") String volunteerId) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        DatabaseReference ref;
        String refString = "clients/" + apiKey + "/volunteers" + volunteerId;
        ref = FirebaseDatabase.getInstance().getReference(refString);
        ApiFuture<Void> future = ref.removeValueAsync();
        future.get();
        return new ResponseEntity<>("Deleted Volunteer ID" + volunteerId, HttpStatus.OK);
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

