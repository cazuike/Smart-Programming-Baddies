package com.smartprogrammingbaddies.Event;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartprogrammingbaddies.AuthController;
import com.smartprogrammingbaddies.Organization.Organization;
import com.smartprogrammingbaddies.StorageCenter.StorageCenter;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains the EventController class.
 */
@RestController
public class EventController {

  @Autowired
  private AuthController auth;
  @Autowired
  EventRepository eventRepository;

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
  public ResponseEntity<?> createEvent(
    @RequestParam("name") String name,
    @RequestParam("description") String description,
    @RequestParam("date") String date,
    @RequestParam("time") String time,
    @RequestParam("location") String location,
    @RequestParam("storageId") String storageId,
    @RequestParam("organizerId") String organizerId) {
    try {
      Date eventDate = new Date();
      Date eventTime = new Date();
      Event event = new Event(name, description, eventDate, eventTime, location, null, null, new HashSet<>());
      Event savedEvent = eventRepository.save(event);
      String message = "Event with ID: " + savedEvent.getDatabaseId() + " was created successfully";
      return new ResponseEntity<>(message, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Retrieves an event for a database.
   *
   * @param apiKey A {@code String} representing the API key.
   * @param eventId A {@code String} representing the event's ID.
   *
   * @return A {@code ResponseEntity} A message if the Event was successfully rertrieved
     and a HTTP 200 response or, HTTP 404 reponse if API Key was not found.
   */
  @GetMapping("/retrieveEvent")
  public ResponseEntity<?> retrieveEvent(@RequestParam("apiKey") String apiKey,
      @RequestParam("eventId") String eventId) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = db.collection("clients").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
          System.out.println(document.getId() + " => " + document.toObject(Event.class));
        }
      }
      return new ResponseEntity<>("Invalid API key", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Removes an event from the database.
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
      if (!validApiKey) {
        return new ResponseEntity<>("Invalid API key.", HttpStatus.UNAUTHORIZED);
      }
      boolean validEvent = verifyEvent(eventId, apiKey).get();
      if (!validEvent) {
        return new ResponseEntity<>("Event not found with ID: " + eventId, HttpStatus.NOT_FOUND);
      }
      // Build the Firebase reference to the event
      String refString = "clients/" + apiKey + "/events/" + eventId;
      DatabaseReference ref = FirebaseDatabase.getInstance().getReference(refString);
      ApiFuture<Void> future = ref.removeValueAsync();
      future.get(); // Blocks until the deletion is complete or throws an error
      return new ResponseEntity<>("Deleted Event with ID: " + eventId, HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>("Error removing event: "
      + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Verifies that the event exists in our database.

   * @param eventId A {@code String} representing the API key.
   * @param apiKey A {@code String} representing the event ID.
   * @return A {@code CompletableFuture<Boolean>} True if exists, otherwise false.
   */

  public CompletableFuture<Boolean> verifyEvent(String eventId, String apiKey) {
    CompletableFuture<Boolean> futureResult = new CompletableFuture<>();

    String refString = "clients/" + apiKey + "/events/" + eventId;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(refString);

    ref.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            futureResult.complete(snapshot.exists());
        }

        @Override
        public void onCancelled(DatabaseError error) {
            futureResult.completeExceptionally(new Exception("Error verifying event: "
                + error.getMessage()));
        }
    });

    return futureResult;
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
  private CompletableFuture<Event> getEventInfo(DatabaseReference ref) {
    CompletableFuture<Event> future = new CompletableFuture<>();

    ref.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        try {
          future.complete(extractEvent(dataSnapshot.getValue().toString()));
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

  /**
   * Convert string into Event Class
   *
   * @param input A string input containing raw class information
   * @return A {@code Event} An event class object containing
   * info about the event specified.
   */
  public static Event extractEvent(String input) {
    System.out.println("test" + input);
    // TODO: configurate to work with a non null list of volunteers.
    String parsedInput = input.replace("{", "").replace("}", "");
    String[] pairs = parsedInput.split(", ");

    String name = null, description = null, date = null, time = null, location = null;
    StorageCenter organizer = null;

    for (String pair : pairs) {
      String[] keyValue = pair.split("=");
      String key = keyValue[0].trim();
      String value = keyValue.length > 1 ? keyValue[1].trim() : null;

      switch (key) {
        case "name":
          name = value;
          break;
        case "description":
          description = value;
          break;
        case "date":
          date = value;
          break;
        case "time":
          time = value;
          break;
        case "location":
          location = value;
          break;
        case "organizer":
          String organizerName = value.replace("name=", "").trim();
          organizer = new StorageCenter(organizerName);
          break;
        case "volunteerCount":
          int volunteerCount = Integer.parseInt(value);
          break;
      }
    }
    return null;
}
}

