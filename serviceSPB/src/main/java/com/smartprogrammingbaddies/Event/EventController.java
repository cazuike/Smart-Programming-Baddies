package com.smartprogrammingbaddies.event;

import com.smartprogrammingbaddies.volunteer.Volunteer;
import java.util.Date;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains the EventController class.
 */
@RestController
public class EventController {
  @Autowired
  EventRepository eventRepository;

  /**
   * Enrolls a event into the database.
   *
   * @param apiKey      A {@code String} representing the API key.
   * @param name        A {@code String} representing the Event's name.
   * @param description A {@code String} representing the event's description.
   * @param date        A {@code String} representing the event's date.
   * @param time        A {@code String} representing the event's time.
   * @param location    A {@code String} representing the event's location.
   * @param organizer   A {@code String} representing the event's organizer.
   *
   * @return A {@code ResponseEntity} A message if the Event was successfully
   *         created
   *         and a HTTP 200 response or, HTTP 404 reponse if API Key was not
   *         found.
   */
  @PostMapping("/createEvent")
  public ResponseEntity<?> createEvent(
      @RequestParam("name") String name,
      @RequestParam("description") String description,
      @RequestParam("date") String date,
      @RequestParam("time") String time,
      @RequestParam("location") String location) {
    try {
      Date eventDate = new Date();
      Date eventTime = new Date();
      HashSet<Volunteer> volunteers = new HashSet<>();
      /*TODO: Add the storageCenter and Organization reference once they are created*/
      Event event;
      event = new Event(name, description, eventDate, eventTime, location, null, null, volunteers);
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
   * @param eventId A {@code String} representing the event's ID.
   * @return A {@code ResponseEntity} A message if the Event was successfully
   *         rertrieved
   *         and a HTTP 200 response or, HTTP 404 reponse if API Key was not
   *         found.
   */
  @GetMapping("/retrieveEvent")
  public ResponseEntity<?> retrieveEvent(@RequestParam("eventId") String eventId) {
    Event event = eventRepository.findById(Integer.parseInt(eventId)).orElse(null);
    if (event == null) {
      return new ResponseEntity<>("Event not found with ID: " + eventId, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(event, HttpStatus.OK);
  }

  /**
   * Adds a volunteer to an existing event in the database.
   *
   * @param apiKey          A {@code String} representing the API key to authenticate the request.
   * @param eventId         A {@code String} representing the unique identifier of the event.
   * @param volunteerName   A {@code String} representing the name of the volunteer being added.
   * @param volunteerRole   A {@code String} representing the role of the volunteer in the event.
   * @param schedule        A {@code Map<String, String>} containing the volunteer's schedule; the
   *                        key is the date and the value is the time.
   * @return                A {@code ResponseEntity<?>} with a message indicating if the volunteer
   *                        was added, along with the HTTP status code.
   *                        - Returns HTTP 200 (OK) if the volunteer was added successfully.
   *                        - Returns HTTP 404 (Not Found) if the API key is invalid or
   *                        the event was not found.
   */
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
      DatabaseReference ref;
      String refString = "clients/" + apiKey + "/events/" + eventId;
      ref = FirebaseDatabase.getInstance().getReference(refString);
      CompletableFuture<Event> future = getEventInfo(ref);
      Event event = future.get();
      if (event != null) {
        // Create the Volunteer object
        Volunteer volunteer = new Volunteer(volunteerName, volunteerRole,
            String.valueOf(System.currentTimeMillis() / 1000), schedule);

        // Add the volunteer to the event
        event.addVolunteer(volunteer);
        // Save the updated event back to Firebase
        ref.setValueAsync(event);
        return new ResponseEntity<>("Volunteer added to event", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Removes an event from the database.
   *
   * @param eventId A {@code String} representing the event's ID.
   * @return A {@code ResponseEntity} A message if the Event was successfully
   *         deleted
   *         and a HTTP 200 response or, HTTP 404 reponse if API Key was not
   *         found.
   */
  @DeleteMapping("/removeEvent")
  public ResponseEntity<?> removeEvent(
      @RequestParam("eventId") String eventId) {
    try {
      eventRepository.deleteById(Integer.parseInt(eventId));
      boolean deleted = !eventRepository.existsById(Integer.parseInt(eventId));
      if (!deleted) {
        String message = "Event with ID: " + eventId + " was not deleted";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
      }
      String message = "Event with ID: " + eventId + " was deleted successfully";
      return new ResponseEntity<>(message, HttpStatus.OK);
    } catch (NumberFormatException e) {
      return new ResponseEntity<>("Invalid Event ID", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>("Error removing event: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Verifies that the event exists in our database.
   *
   * @param eventId A {@code String} representing the API key.
   * @param apiKey  A {@code String} representing the event ID.
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
}


