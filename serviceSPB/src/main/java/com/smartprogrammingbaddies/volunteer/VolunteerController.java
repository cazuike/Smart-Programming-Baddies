package com.smartprogrammingbaddies.volunteer;

import com.smartprogrammingbaddies.auth.AuthController;
import java.util.Map;
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

  @Autowired
  VolunteerRepository volunteerRepository;

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
                                            @RequestBody Map<String, String> schedule) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).getStatusCode() == HttpStatus.OK;
      if (!validApiKey) {
        return new ResponseEntity<>("Invalid API key.", HttpStatus.FORBIDDEN);
      }

      String dateSignUp = String.valueOf(System.currentTimeMillis());
      Volunteer volunteer = new Volunteer(name, role, dateSignUp, schedule);

      Volunteer savedVolunteer = volunteerRepository.save(volunteer);
      String message = "Volunteer enrolled with ID: " + savedVolunteer.getVolunteerId();
      return ResponseEntity.ok(message);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
    * Remove a volunteer from the database.
    *
    * @param apiKey A {@code String} representing the API key.
    * @param volunteerId A {@code String} representing the volunteer ID.
    * @return A {@code ResponseEntity} A message if Volunteer was successfully removed
  and a HTTP 200 response or, HTTP 404 response if API Key was not found.
    */
  @DeleteMapping("/removeVolunteer")
  public ResponseEntity<?> removeVolunteer(@RequestParam("apiKey") String apiKey,
                                            @RequestParam("volunteerId") int volunteerId) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).getStatusCode() == HttpStatus.OK;
      if (!validApiKey) {
        return new ResponseEntity<>("Invalid API key.", HttpStatus.FORBIDDEN);
      }

      if (!volunteerRepository.existsById(volunteerId)) {
        return new ResponseEntity<>("Volunteer not found.", HttpStatus.NOT_FOUND);
      }

      volunteerRepository.deleteById(volunteerId);

      return new ResponseEntity<>("Volunteer successfully removed.", HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }


  /**
    * Update schedule of volunteer.
    *
    * @param apiKey A {@code String} representing the API key.
    * @param volunteerId A {@code String} representing the volunteer ID.
    * @return A {@code ResponseEntity} A message if Volunteer was successfully removed
  and a HTTP 200 response or, HTTP 404 response if API Key was not found.
    */
  @PatchMapping("/updateSchedule")
  public ResponseEntity<?> updateSchedule(@RequestParam("apiKey") String apiKey,
                                          @RequestParam("volunteerId") int volunteerId,
                                          @RequestBody Map<String, String> newSchedule) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).getStatusCode() == HttpStatus.OK;
      if (!validApiKey) {
        return new ResponseEntity<>("Invalid API key.", HttpStatus.FORBIDDEN);
      }

      Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new IllegalArgumentException("Volunteer not found."));

      volunteer.updateSchedule(newSchedule);
      volunteerRepository.save(volunteer);

      return new ResponseEntity<>("Volunteer schedule updated successfully.", HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }


  /**
    * Update role of volunteer.
    *
    * @param apiKey A {@code String} representing the API key.
    * @param volunteerId A {@code String} representing the volunteer ID.
    * @return A {@code ResponseEntity} A message if Volunteer was successfully removed
  and a HTTP 200 response or, HTTP 404 reponse if API Key was not found.
    */
  @PatchMapping("/updateRole")
  public ResponseEntity<?> updateRole(@RequestParam("apiKey") String apiKey,
                                      @RequestParam("volunteerId") int volunteerId,
                                      @RequestParam("role") String newRole) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).getStatusCode() == HttpStatus.OK;
      if (!validApiKey) {
        return new ResponseEntity<>("Invalid API key.", HttpStatus.FORBIDDEN);
      }

      Volunteer volunteer = volunteerRepository.findById(volunteerId)
              .orElseThrow(() -> new IllegalArgumentException("Volunteer not found."));

      volunteer.setRole(newRole);
      volunteerRepository.save(volunteer);

      return new ResponseEntity<>("Volunteer role updated successfully.", HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
    * Update name of volunteer.
    *
    * @param apiKey A {@code String} representing the API key.
    * @param volunteerId A {@code String} representing the volunteer ID.
    * @return A {@code ResponseEntity} A message if Volunteer was successfully removed
  and a HTTP 200 response or, HTTP 404 reponse if API Key was not found.
    */
  @PatchMapping("/updateName")
  public ResponseEntity<?> updateName(@RequestParam("apiKey") String apiKey,
                                      @RequestParam("volunteerId") int volunteerId,
                                      @RequestParam("name") String newName) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).getStatusCode() == HttpStatus.OK;
      if (!validApiKey) {
        return new ResponseEntity<>("Invalid API key.", HttpStatus.FORBIDDEN);
      }

      Volunteer volunteer = volunteerRepository.findById(volunteerId)
              .orElseThrow(() -> new IllegalArgumentException("Volunteer not found."));

      volunteer.setName(newName);
      volunteerRepository.save(volunteer);

      return new ResponseEntity<>("Volunteer name updated successfully.", HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
    * Get volunteer info.
    *
    * @param apiKey A {@code String} representing the API key.
    * @param volunteerId A {@code String} representing the volunteer ID.
    * @return A {@code ResponseEntity} A message if Volunteer was successfully removed
  and a HTTP 200 response or, HTTP 404 reponse if API Key was not found.
    */
  @GetMapping("/getVolunteerInfo")
  public ResponseEntity<?> getVolunteerInfo(@RequestParam("apiKey") String apiKey,
                                            @RequestParam("volunteerId") int volunteerId) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).getStatusCode() == HttpStatus.OK;
      if (!validApiKey) {
        return new ResponseEntity<>("Invalid API key.", HttpStatus.FORBIDDEN);
      }

      Volunteer volunteer = volunteerRepository.findById(volunteerId)
              .orElseThrow(() -> new IllegalArgumentException("Volunteer not found."));

      return new ResponseEntity<>(volunteer.toString(), HttpStatus.OK);
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
}
