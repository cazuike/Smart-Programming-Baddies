package com.smartprogrammingbaddies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Datastore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;

/**
 * This class contains the EventController class.
 */
@RestController
public class EventController {

  @Autowired
  private AuthController auth;

  /**
   * Enrolls a event into the database.
   *
   * @param apiKey A {@code String} representing the API key.
   * @param name A {@code String} representing the Event's name.
   * @param description A {@code String} representing the event's description.
   * @param date A {@code String} representing the event's date.
   * @param time A {@code String} representing the event's time.
   * @param location A {@code String} representing the event's location.
   * @param organizer A {@code String} representing the event's organizer.
   *
   * @return A {@code ResponseEntity} A message if the Event was successfully created
     and a HTTP 200 response or, HTTP 404 reponse if API Key was not found.
   */
  @PostMapping("/createEvent")
  public ResponseEntity<?> createEvent(@RequestParam("apiKey") String apiKey,
      @RequestParam("name") String name,
      @RequestParam("description") String description,
      @RequestParam("date") String date,
      @RequestParam("time") String time,
      @RequestParam("location") String location,
      @RequestParam("organizer") String organizer) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        DatabaseReference ref;
        String refString = "clients/" + apiKey + "/events";
        ref = FirebaseDatabase.getInstance().getReference(refString);
        String eventId = UUID.randomUUID().toString();
        StorageCenter storageCenter = new StorageCenter(organizer);
        Event event;
        event = new Event(name, description, date, time, location, storageCenter, new HashMap<>());
        Map<String, Object> clientData = new HashMap<>();
        clientData.put("event", event);
        ApiFuture<Void> future = ref.child(eventId).setValueAsync(clientData);
        future.get();
        return new ResponseEntity<>("Event ID: " + eventId, HttpStatus.OK);
      }
      return new ResponseEntity<>("Invalid API key", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Enrolls a event into the database.
   *
   * @param apiKey A {@code String} representing the API key.
   * @param eventId A {@code String} representing the event's ID.
   *
   * @return A {@code ResponseEntity} A message if the Event was successfully deleted
     and a HTTP 200 response or, HTTP 404 reponse if API Key was not found.
   */
  @GetMapping("/retrieveEvent")
  public ResponseEntity<?> retrieveEvent(@RequestParam("apiKey") String apiKey,
      @RequestParam("eventId") String eventId) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        DatabaseReference ref;
        String refString = "clients/" + apiKey + "/events/" + eventId;
        ref = FirebaseDatabase.getInstance().getReference(refString);
        CompletableFuture<String> future = getEventInfo(ref);
        String eventInfo = future.get();
        return new ResponseEntity<>(eventInfo, HttpStatus.OK);
      }
      return new ResponseEntity<>("Invalid API key", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }
  
  @PatchMapping("/addVolunteer")
  public ResponseEntity<?> addVolunteer(@RequestParam("apiKey") String apiKey,
                                        @RequestParam("eventId") String eventId,
                                        @RequestParam("volunteerName") String volunteerName,
                                        @RequestParam("volunteerRole") String volunteerRole,
                                        @RequestBody Map<String, String> schedule) {
      try {
          // Verify the API Key
          boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
          if (!validApiKey) {
              return new ResponseEntity<>("Invalid API key", HttpStatus.NOT_FOUND);
          }
          DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("clients/" + apiKey + "/events/" + eventId);
          CompletableFuture<String> future = getEventInfo(eventRef);
          String eventInfo = future.get();
          System.out.println(eventInfo);
          if (eventInfo != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            Event event = objectMapper.readValue(eventInfo, Event.class);
            System.out.println("Hi" + event.toString());
            Volunteer volunteer = new Volunteer(volunteerName, volunteerRole, String.valueOf(System.currentTimeMillis() / 1000), schedule);              
            event.addVolunteer(volunteer);
            eventRef.setValueAsync(event);
            return new ResponseEntity<>("Volunteer added to event", HttpStatus.OK);
          } else {
              return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
          }
      } catch (Exception e) {
          return handleException(e);
      }
  }
  
  /**
   * Enrolls a event into the database.
   *
   * @param apiKey A {@code String} representing the API key.
   * @param eventId A {@code String} representing the event's ID.
   *
   * @return A {@code ResponseEntity} A message if the Event was successfully deleted
     and a HTTP 200 response or, HTTP 404 reponse if API Key was not found.
   */
  @DeleteMapping("/removeEvent")
  public ResponseEntity<?> removeEvent(@RequestParam("apiKey") String apiKey,
      @RequestParam("eventId") String eventId) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        DatabaseReference ref;
        String refString = "clients/" + apiKey + "/events/" + eventId;
        ref = FirebaseDatabase.getInstance().getReference(refString);
        ApiFuture<Void> future = ref.removeValueAsync();
        future.get();
        return new ResponseEntity<>("Deleted Event with ID: "
        + eventId, HttpStatus.OK);
      }
      return new ResponseEntity<>("Invalid API key", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Deserialize JSON data from remote database into a String,
   * return info in readable format.
   *
   * @param ref A {@code DatabaseReference} representing node in database where info is stored.
   * @return A {@code CompletableFuture<String>} A string containing
   *         information about the event specified.
   */
  private CompletableFuture<String> getEventInfo(DatabaseReference ref) {
    CompletableFuture<String> future = new CompletableFuture<>();

    ref.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        try {
          future.complete(dataSnapshot.getValue().toString());
        } catch (Exception e) {
          handleException(e);
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        handleException(databaseError.toException());
      }
    });
    return future;
  }
}

