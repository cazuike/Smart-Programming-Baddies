package com.smartprogrammingbaddies;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     and a HTTP 200 response or, HTTP 404 response if API Key was not found.
   */
  @PatchMapping("/enrollVolunteer")
  public ResponseEntity<?> enrollVolunteer(@RequestParam("apiKey") String apiKey,
      @RequestParam("name") String name,
      @RequestParam("role") String role,
       @RequestBody Map<String, String> schedule
  ) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        DatabaseReference ref;
        String refString = "clients/" + apiKey + "/volunteers";
        ref = FirebaseDatabase.getInstance().getReference(refString);
        String volunteerId = UUID.randomUUID().toString();
        Volunteer volunteer = new Volunteer(name, role,
                "" + System.currentTimeMillis() / 1000, schedule);
        ApiFuture<Void> future = ref.child(volunteerId).setValueAsync(volunteer);
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
     and a HTTP 200 response or, HTTP 404 response if API Key was not found.
   */
  @DeleteMapping("/removeVolunteer")
  public ResponseEntity<?> removeVolunteer(@RequestParam("apiKey") String apiKey,
                                            @RequestParam("volunteerId") String volunteerId) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        boolean validVolunteer = verifyVolunteer(volunteerId, apiKey).get();
        if (!validVolunteer) {
          return new ResponseEntity<>("Unable to verify volunteer ID.", HttpStatus.NOT_FOUND);
        }
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

  /**
   * Removes a volunteer from the database.
   *
   * @param apiKey A {@code String} representing the API key.
   * @param volunteerId A {@code String} representing the volunteer ID.
   * @return A {@code ResponseEntity} A message if Volunteer was successfully removed
  and a HTTP 200 response or, HTTP 404 response if API Key was not found.
   */
  @PatchMapping("/updateSchedule")
  public ResponseEntity<?> updateSchedule(@RequestParam("apiKey") String apiKey,
                                           @RequestParam("volunteerId") String volunteerId,
                                          @RequestBody Map<String, String> schedule
                                          ) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        boolean validVolunteer = verifyVolunteer(volunteerId, apiKey).get();
        if (!validVolunteer) {
          return new ResponseEntity<>("Unable to verify volunteer ID.", HttpStatus.NOT_FOUND);
        }
        DatabaseReference ref;
        String refString = "clients/" + apiKey + "/volunteers" + volunteerId;
        ref = FirebaseDatabase.getInstance().getReference(refString);
        updateField(ref, "schedule", schedule);
        return new ResponseEntity<>("Updated Schedule", HttpStatus.OK);
      }
      return new ResponseEntity<>("Unable to verify api key.", HttpStatus.NOT_FOUND);
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
  @PatchMapping("/updateRole")
  public ResponseEntity<?> updateRole(@RequestParam("apiKey") String apiKey,
                                          @RequestParam("volunteerId") String volunteerId,
                                      @RequestParam("role") String role
  ) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        boolean validVolunteer = verifyVolunteer(volunteerId, apiKey).get();
        if (!validVolunteer) {
          return new ResponseEntity<>("Unable to verify volunteer ID.", HttpStatus.NOT_FOUND);
        }
        DatabaseReference ref;
        String refString = "clients/" + apiKey + "/volunteers" + volunteerId;
        ref = FirebaseDatabase.getInstance().getReference(refString);
        updateField(ref, "role", role);
        return new ResponseEntity<>("Updated Schedule", HttpStatus.OK);
      }
      return new ResponseEntity<>("Unable to verify api key.", HttpStatus.NOT_FOUND);
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
  @PatchMapping("/updateName")
  public ResponseEntity<?> updateName(@RequestParam("apiKey") String apiKey,
                                      @RequestParam("volunteerId") String volunteerId,
                                      @RequestParam("name") String name
  ) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        boolean validVolunteer = verifyVolunteer(volunteerId, apiKey).get();
        if (!validVolunteer) {
          return new ResponseEntity<>("Unable to verify volunteer ID.", HttpStatus.NOT_FOUND);
        }
        DatabaseReference ref;
        String refString = "clients/" + apiKey + "/volunteers" + volunteerId;
        ref = FirebaseDatabase.getInstance().getReference(refString);
        updateField(ref, "name", name);
        return new ResponseEntity<>("Updated Schedule", HttpStatus.OK);
      }
      return new ResponseEntity<>("Unable to verify api key.", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Retrieve info about volunteer.
   *
   * @param apiKey A {@code String} representing the API key.
   * @param volunteerId A {@code String} representing the volunteer ID.
   * @return A {@code ResponseEntity} A string containing the volunteer info
  and a HTTP 200 response or, HTTP 404 response if API Key was not found.
   */
  @GetMapping("/retrieveVolunteer")
  public ResponseEntity<?> idVolunteer(@RequestParam("apiKey") String apiKey,
                                      @RequestParam("volunteerId") String volunteerId
  ) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        boolean validVolunteer = verifyVolunteer(volunteerId, apiKey).get();
        if (!validVolunteer) {
          return new ResponseEntity<>("Unable to verify volunteer ID.", HttpStatus.NOT_FOUND);
        }
        DatabaseReference ref;
        String refString = "clients/" + apiKey + "/volunteers" + volunteerId;
        ref = FirebaseDatabase.getInstance().getReference(refString);
        String volunteerData = getVolunteerInfo(ref).get();
        return new ResponseEntity<>(volunteerData, HttpStatus.OK);
      }
      return new ResponseEntity<>("Unable to verify api key.", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Handle Exception.
   *
   * @param e A {@code Exception} Exception.
   * @return A {@code ResponseEntity<?>} Response Entity..
   */
  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Update field of volunteer.
   *
   * @param dbRef A {@code DatabaseReference} reference to a volunteer node.
   * @param field A {@code String} representing the API key.
   * @param newValue A {@code Object} representing the changed value.
   */
  public void updateField(DatabaseReference dbRef, String field, Object newValue) throws Exception {
    CompletableFuture<Void> future = new CompletableFuture<>();

    dbRef.child(field).setValue(newValue, (error, ref) -> {
      if (error != null) {
        future.completeExceptionally(new RuntimeException("Update failed: ", error.toException()));
      } else {
        future.complete(null);
      }
    });

    try {
      future.get();
    } catch (Exception e) {
      throw new Exception("Update failed: ", e);
    }
  }

  /**
   * Verify if volunteer exists in our database.
   *
   * @param apiKey A {@code String} representing the API key.
   * @param volunteerId A {@code String} representing the volunteer ID.
   * @return A {@code CompletableFuture<Boolean>} True if exists, otherwise false.
   */
  private CompletableFuture<Boolean> verifyVolunteer(String volunteerId, String apiKey) {
    CompletableFuture<Boolean> future = new CompletableFuture<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clients/"
            + apiKey + "/volunteers").child(volunteerId);

    ref.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
          future.complete(true);
        } else {
          future.complete(false);
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        System.err.println("error:" + databaseError.getMessage());
      }
    });
    return future;
  }

  /**
   * Deserialize JSON data from remote database into Volunteer.class,
   * return info in readable format.
   *
   * @param ref A {@code DatabaseReference} representing node in database where info is stored.
   * @return A {@code CompletableFuture<String>} A string containing
   *         information about the volunteer.
   */
  public CompletableFuture<String> getVolunteerInfo(DatabaseReference ref) {
    CompletableFuture<String> future = new CompletableFuture<>();

    ref.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        try {
          Volunteer volunteer = dataSnapshot.getValue(Volunteer.class);
          future.complete(volunteer.toString());
        } catch (Exception e) {
          System.err.println("Encountered deserialization error.");
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        System.err.println("error:" + databaseError.getMessage());
      }
    });
    return future;
  }
}

