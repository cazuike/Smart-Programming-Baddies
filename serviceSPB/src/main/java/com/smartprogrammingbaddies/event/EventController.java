package com.smartprogrammingbaddies.event;

import com.smartprogrammingbaddies.auth.AuthController;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import com.smartprogrammingbaddies.storagecenter.StorageCenterRepository;
import com.smartprogrammingbaddies.utils.TimeSlot;
import com.smartprogrammingbaddies.volunteer.Volunteer;
import com.smartprogrammingbaddies.volunteer.VolunteerRepository;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains the EventController class.
 */
//for testing
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class EventController {  
  @Autowired
  EventRepository eventRepository;

  @Autowired
  StorageCenterRepository storageCenterRepository;

  @Autowired
  VolunteerRepository volunteerRepository;

  @Autowired
  private AuthController auth;

  /**
   * Enrolls an event into the database.
   * This method creates a new event with the provided details, including the
   * event's name,
   * description, date, time, location, associated storage center, and
   * organization.
   * It validates the API key and checks the validity of the provided storage
   * center ID.
   *
   * @param apiKey          A {@code String} representing the API key for
   *                        authentication.
   * @param name            A {@code String} representing the event's name.
   * @param description     A {@code String} representing the event's description.
   * @param date            A {@code String} representing the event's date in the
   *                        format YYYY-MM-DD.
   * @param startTime       A {@code String} representing the event's start time
   *                        in the format HH:mm.
   * @param endTime         A {@code String} representing the event's end time in
   *                        the format HH:mm.
   * @param location        A {@code String} representing the event's location.
   * @param storageCenterId An {@code int} representing the ID of the associated
   *                        storage center.
   * @param organizationId  An {@code int} representing the ID of the associated
   *                        organization
   *                        (currently unused and passed as null).
   *
   * @return A {@code ResponseEntity} containing a success message and an HTTP 200
   *         response if the event
   *         was successfully created. Returns an HTTP 404 response if the API key
   *         or storage center ID is invalid,
   *         or an HTTP 500 response if an error occurs.
   */
  @PostMapping("/createEvent")
  public ResponseEntity<?> createEvent(@RequestParam("apiKey") String apiKey,
      @RequestParam("name") String name,
      @RequestParam("description") String description,
      @RequestParam("date") String date,
      @RequestParam("startTime") String startTime,
      @RequestParam("endTime") String endTime,
      @RequestParam("location") String location,
      @RequestParam("storageCenterId") int storageCenterId,
      @RequestParam("organizationId") int organizationId) {
    try {
      if (auth.verifyApiKey(apiKey).getStatusCode() != HttpStatus.OK) {
        return new ResponseEntity<>("Invalid API key", HttpStatus.UNAUTHORIZED);
      }

      DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
      LocalTime parsedStartTime = LocalTime.parse(startTime, timeFormatter);
      LocalTime parsedEndTime = LocalTime.parse(endTime, timeFormatter);
      TimeSlot timeSlot = new TimeSlot(parsedStartTime, parsedEndTime);

      StorageCenter storageCenter = storageCenterRepository.findById(storageCenterId).orElse(null);
      if (storageCenter == null) {
        return new ResponseEntity<>("Invalid Storage Center Id", HttpStatus.NOT_FOUND);
      }

      HashSet<Volunteer> volunteers = new HashSet<>();

      Event event;
      event = new Event(name, description, date, timeSlot, location, storageCenter,
          null, volunteers);
      Event savedEvent = eventRepository.save(event);
      String message = "Event was created successfully with ID: " + savedEvent.getDatabaseId();
      return new ResponseEntity<>(message, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Adds a volunteer to an event.
   *
   * @param apiKey      A {@code String} representing the API key for
   *                    authentication.
   * @param eventId     A {@code int} representing the ID of the event.
   * @param volunteerId A {@code int} representing the ID of the volunteer to add.
   *
   * @return A {@code ResponseEntity} with a success message if the volunteer is
   *         added,
   *         or an error message if the event or volunteer is not found.
   */
  @PostMapping("/addVolunteerToEvent")
  public ResponseEntity<?> addVolunteerToEvent(@RequestParam("apiKey") String apiKey,
      @RequestParam("eventId") int eventId,
      @RequestParam("volunteerId") int volunteerId) {
    // Validate API key
    if (auth.verifyApiKey(apiKey).getStatusCode() != HttpStatus.OK) {
      return new ResponseEntity<>("Invalid API key", HttpStatus.UNAUTHORIZED);
    }

    Event event = eventRepository.findById(eventId).orElse(null);
    if (event == null) {
      return new ResponseEntity<>("Event not found with ID: " + eventId, HttpStatus.NOT_FOUND);
    }

    Volunteer volunteer = volunteerRepository.findById(volunteerId).orElse(null);
    if (volunteer == null) {
      return new ResponseEntity<>("Volunteer not found with ID: " + volunteerId,
          HttpStatus.NOT_FOUND);
    }
    event.addVolunteer(volunteer);
    eventRepository.save(event);

    return new ResponseEntity<>("Volunteer added successfully to event with ID: " + eventId,
        HttpStatus.OK);
  }

  /**
   * Lists all events in the database.
   *
   * @param apiKey A {@code String} representing the API key for authentication.
   *
   * @return A {@code ResponseEntity} containing a list of all events
   *         if the API key is valid, along with an HTTP 200 response.
   *         Returns an HTTP 404 response with an error message if the API key is
   *         invalid.
   */
  @GetMapping("/listEvents")
  public ResponseEntity<?> listEvents(@RequestParam("apiKey") String apiKey) {
    if (auth.verifyApiKey(apiKey).getStatusCode() != HttpStatus.OK) {
      return new ResponseEntity<>("Invalid API key", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(eventRepository.findAll(), HttpStatus.OK);
  }

  /**
   * Retrieves an event for a database.
   *
   * @param eventId A {@code String} representing the event's ID.
   *
   * @return A {@code ResponseEntity} A message if the Event was successfully
   *         rertrieved
   *         and a HTTP 200 response or, HTTP 404 reponse if API Key was not
   *         found.
   */
  @GetMapping("/retrieveEvent")
  public ResponseEntity<?> retrieveEvent(@RequestParam("apiKey") String apiKey,
      @RequestParam("eventId") String eventId) {
    if (auth.verifyApiKey(apiKey).getStatusCode() != HttpStatus.OK) {
      return new ResponseEntity<>("Invalid API key", HttpStatus.UNAUTHORIZED);
    }
    Event event = eventRepository.findById(Integer.parseInt(eventId)).orElse(null);
    if (event == null) {
      return new ResponseEntity<>("Event not found with ID: " + eventId, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(event.toString(), HttpStatus.OK);
  }

  /**
   * Removes an event from the database.
   *
   * @param eventId A {@code String} representing the event's ID.
   *
   * @return A {@code ResponseEntity} A message if the Event was successfully
   *         deleted
   *         and a HTTP 200 response or, HTTP 404 reponse if API Key was not
   *         found.
   */
  @DeleteMapping("/removeEvent")
  public ResponseEntity<?> removeEvent(@RequestParam("apiKey") String apiKey,
      @RequestParam("eventId") String eventId) {
    try {
      if (auth.verifyApiKey(apiKey).getStatusCode() != HttpStatus.OK) {
        return new ResponseEntity<>("Invalid API key", HttpStatus.UNAUTHORIZED);
      }
      if (!eventRepository.existsById(Integer.parseInt(eventId))) {
        return new ResponseEntity<>("Event with ID: " + eventId + " does not exist", HttpStatus.NOT_FOUND);
    }
      eventRepository.deleteById(Integer.parseInt(eventId));
      String message = "Event with ID: " + eventId + " was deleted successfully";
      return new ResponseEntity<>(message, HttpStatus.OK);
    } catch (NumberFormatException e) {
      return new ResponseEntity<>("Invalid Event ID", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Searches for all events on a particular date.
   *
   * @param apiKey A {@code String} representing the API key for authentication.
   * @param date   A {@code String} representing the date to search for events.
   *
   * @return A {@code ResponseEntity} containing a list of events on the specified
   *         date
   *         if the API key is valid, along with an HTTP 200 response.
   *         Returns an HTTP 404 response with an error message if the API key is
   *         invalid
   *         or if no events are found on the specified date.
   */
  @GetMapping("/searchEventsByDate")
  public ResponseEntity<?> searchEventsByDate(@RequestParam("apiKey") String apiKey,
      @RequestParam("date") String date) {
    try {
      // Validate API key
      if (auth.verifyApiKey(apiKey).getStatusCode() != HttpStatus.OK) {
        return new ResponseEntity<>("Invalid API key", HttpStatus.NOT_FOUND);
      }
      var events = eventRepository.findByDate(date);

      if (events.isEmpty()) {
        return new ResponseEntity<>("No events found on the specified date: " + date,
            HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(events, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Searches for all events on a particular date.
   *
   * @param apiKey   A {@code String} representing the API key for authentication.
   * @param location A {@code String} representing the location to search for
   *                 events.
   *
   * @return A {@code ResponseEntity} containing a list of events on the specified
   *         date
   *         if the API key is valid, along with an HTTP 200 response.
   *         Returns an HTTP 404 response with an error message if the API key is
   *         invalid
   *         or if no events are found on the specified date.
   */
  @GetMapping("/searchEventsByLocation")
  public ResponseEntity<?> searchEventsByLocation(@RequestParam("apiKey") String apiKey,
      @RequestParam("location") String location) {
    try {
      // Validate API key
      if (auth.verifyApiKey(apiKey).getStatusCode() != HttpStatus.OK) {
        return new ResponseEntity<>("Invalid API key", HttpStatus.NOT_FOUND);
      }

      // Fetch events by location
      var events = eventRepository.findByLocationContainingIgnoreCase(location);

      if (events.isEmpty()) {
        return new ResponseEntity<>("No events found on the specified location: " + location,
            HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(events, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
