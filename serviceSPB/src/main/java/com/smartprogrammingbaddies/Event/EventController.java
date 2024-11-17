package com.smartprogrammingbaddies.event;

import com.smartprogrammingbaddies.volunteer.Volunteer;
import com.smartprogrammingbaddies.event.EventRepository;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
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
  EventRepository eventRepository;

  /**
   * Enrolls a event into the database.
   *
   * @param name A {@code String} representing the Event's name.
   * @param description A {@code String} representing the event's description.
   * @param date A {@code String} representing the event's date in Java Date Format.
   * @param time A {@code Date} representing the event's time in Java Time Format.
   * @param location A {@code Date} representing the event's location.
   *
   * @return A {@code ResponseEntity} A message if the Event was successfully created
     and a HTTP 200 response or, HTTP 500 reponse if an error occurred.
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
      // event = new Event(name, description, eventDate, eventTime, location, null, null, volunteers);
      // Event savedEvent = eventRepository.save(event);
      // String message = "Event with ID: " + savedEvent.getDatabaseId() + " was created successfully";
      return new ResponseEntity<>("Test", HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Retrieves an event for a database.
   *
   * @param eventId A {@code String} representing the event's ID.
   *
   * @return A {@code ResponseEntity} A message if the Event was successfully rertrieved
     and a HTTP 200 response or, HTTP 404 reponse if API Key was not found.
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
   * Removes an event from the database.
   *
   * @param eventId A {@code String} representing the event's ID.
   *
   * @return A {@code ResponseEntity} A message if the Event was successfully deleted
     and a HTTP 200 response or, HTTP 404 reponse if API Key was not found.
   */
  @DeleteMapping("/removeEvent")
  public ResponseEntity<?> removeEvent(@RequestParam("eventId") String eventId) {
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
      return handleException(e);
    }
  }

  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}


