package com.smartprogrammingbaddies;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.api.core.ApiFuture;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import javax.sql.DataSource;

/**
 * This class contains the VolunteerController class.
 */
@RestController
public class VolunteerController {

  @Autowired
  private AuthController auth;

  private final DataSource dataSource;

  @Autowired
  public VolunteerController(DataSource dataSource) {
    this.dataSource = dataSource;
  }
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
      boolean validApiKey = auth.verifyApiKey(apiKey).getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        String volunteerId = UUID.randomUUID().toString();
        Volunteer volunteer = new Volunteer(name, role,
                "" + System.currentTimeMillis() / 1000, schedule);
          ObjectMapper objectMapper = new ObjectMapper();
          String volunteerJson = objectMapper.writeValueAsString(volunteer);

          String insertQuery = "INSERT INTO volunteers (clientID, volunteerID, volunteer_data) VALUES (?, ?, ?)";

          try (Connection connection = dataSource.getConnection();
               PreparedStatement statement = connection.prepareStatement(insertQuery)) {

              statement.setString(1, apiKey);
              statement.setString(2, volunteerId);
              statement.setString(3, volunteerJson);

              statement.executeUpdate();
          }
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
      boolean validApiKey = auth.verifyApiKey(apiKey).getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        String deleteQuery = "DELETE FROM volunteers WHERE volunteerID = ? AND clientID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

          statement.setString(1, volunteerId);
          statement.setString(2, apiKey);

          int rowsDeleted = statement.executeUpdate();
          if (rowsDeleted > 0) {
            return new ResponseEntity<>("Deleted Volunteer ID" + volunteerId, HttpStatus.NOT_FOUND);
          } else {
            return new ResponseEntity<>("Volunteer not found", HttpStatus.NOT_FOUND);
          }
        } catch (SQLException e) {
          e.printStackTrace();
          return new ResponseEntity<>("Failed to delete Volunteer.", HttpStatus.NOT_FOUND);
        }
      }
      else{
          return new ResponseEntity<>("Invalid API key.", HttpStatus.NOT_FOUND);
      }
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
                                           @RequestParam("volunteerId") String volunteerId,
                                          @RequestBody Map<String, String> newSchedule
                                          ) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).getStatusCode() == HttpStatus.OK;
      if (!validApiKey) {
        return new ResponseEntity<>("Invalid API key.", HttpStatus.NOT_FOUND);
      }

      String selectQuery = "SELECT volunteer_data FROM volunteers WHERE volunteerID = ?";
      String volunteerDataJson;

      try (Connection connection = dataSource.getConnection();
           PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {

        selectStatement.setString(1, volunteerId);
        ResultSet resultSet = selectStatement.executeQuery();

        if (resultSet.next()) {
          volunteerDataJson = resultSet.getString("volunteer_data");
        } else {
          return new ResponseEntity<>("Volunteer not found.", HttpStatus.NOT_FOUND);
        }
      }

      ObjectMapper objectMapper = new ObjectMapper();
      Volunteer volunteer = objectMapper.readValue(volunteerDataJson, Volunteer.class);

      volunteer.updateSchedule(newSchedule);

      String updatedVolunteerDataJson = objectMapper.writeValueAsString(volunteer);


      String updateQuery = "UPDATE volunteers SET volunteer_data = ? WHERE volunteerID = ?";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

        updateStatement.setString(1, updatedVolunteerDataJson);
        updateStatement.setString(2, volunteerId);

        int rowsUpdated = updateStatement.executeUpdate();

        if (rowsUpdated > 0) {
          return new ResponseEntity<>("Volunteer schedule updated successfully.", HttpStatus.OK);
        } else {
          return new ResponseEntity<>("Failed to update volunteer schedule.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }

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
  public ResponseEntity<?> updateRole(
          @RequestParam("apiKey") String apiKey,
          @RequestParam("volunteerId") String volunteerId,
          @RequestParam("role") String newRole
  ) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).getStatusCode() == HttpStatus.OK;
      if (!validApiKey) {
        return new ResponseEntity<>("Invalid API key.", HttpStatus.NOT_FOUND);
      }

      // Step 2: Retrieve JSON data from the database for the specified volunteer
      String selectQuery = "SELECT volunteer_data FROM volunteers WHERE volunteerID = ?";
      String volunteerDataJson;

      try (Connection connection = dataSource.getConnection();
           PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {

        selectStatement.setString(1, volunteerId);
        ResultSet resultSet = selectStatement.executeQuery();

        if (resultSet.next()) {
          volunteerDataJson = resultSet.getString("volunteer_data");
        } else {
          return new ResponseEntity<>("Volunteer not found.", HttpStatus.NOT_FOUND);
        }
      }

      ObjectMapper objectMapper = new ObjectMapper();
      Volunteer volunteer = objectMapper.readValue(volunteerDataJson, Volunteer.class);

      volunteer.updateRole(newRole);

      String updatedVolunteerDataJson = objectMapper.writeValueAsString(volunteer);


      String updateQuery = "UPDATE volunteers SET volunteer_data = ? WHERE volunteerID = ?";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

        updateStatement.setString(1, updatedVolunteerDataJson);
        updateStatement.setString(2, volunteerId);

        int rowsUpdated = updateStatement.executeUpdate();

        if (rowsUpdated > 0) {
          return new ResponseEntity<>("Volunteer role updated successfully.", HttpStatus.OK);
        } else {
          return new ResponseEntity<>("Failed to update volunteer role.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }

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
  public ResponseEntity<?> updateName(
          @RequestParam("apiKey") String apiKey,
          @RequestParam("volunteerId") String volunteerId,
          @RequestParam("name") String newName
  ) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).getStatusCode() == HttpStatus.OK;
      if (!validApiKey) {
        return new ResponseEntity<>("Invalid API key.", HttpStatus.NOT_FOUND);
      }

      String selectQuery = "SELECT volunteer_data FROM volunteers WHERE volunteerID = ?";
      String volunteerDataJson;

      try (Connection connection = dataSource.getConnection();
           PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {

        selectStatement.setString(1, volunteerId);
        ResultSet resultSet = selectStatement.executeQuery();

        if (resultSet.next()) {
          volunteerDataJson = resultSet.getString("volunteer_data");
        } else {
          return new ResponseEntity<>("Volunteer not found.", HttpStatus.NOT_FOUND);
        }
      }

      ObjectMapper objectMapper = new ObjectMapper();
      Volunteer volunteer = objectMapper.readValue(volunteerDataJson, Volunteer.class);

      volunteer.updateName(newName);

      String updatedVolunteerDataJson = objectMapper.writeValueAsString(volunteer);


      String updateQuery = "UPDATE volunteers SET volunteer_data = ? WHERE volunteerID = ?";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

        updateStatement.setString(1, updatedVolunteerDataJson);
        updateStatement.setString(2, volunteerId);

        int rowsUpdated = updateStatement.executeUpdate();

        if (rowsUpdated > 0) {
          return new ResponseEntity<>("Volunteer role updated successfully.", HttpStatus.OK);
        } else {
          return new ResponseEntity<>("Failed to update volunteer role.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }

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
  @GetMapping("/getVolunteerInfo")
  public ResponseEntity<?> getVolunteerInfo(@RequestParam("apiKey") String apiKey,
                                          @RequestParam("volunteerId") String volunteerId
  ) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).getStatusCode() == HttpStatus.OK;
      if (!validApiKey) {
        return new ResponseEntity<>("Invalid API key.", HttpStatus.NOT_FOUND);
      }

      String selectQuery = "SELECT volunteer_data FROM volunteers WHERE volunteerID = ?";
      String volunteerDataJson;

      try (Connection connection = dataSource.getConnection();
           PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {

        selectStatement.setString(1, volunteerId);
        ResultSet resultSet = selectStatement.executeQuery();

        if (resultSet.next()) {
          volunteerDataJson = resultSet.getString("volunteer_data");
        } else {
          return new ResponseEntity<>("Volunteer not found.", HttpStatus.NOT_FOUND);
        }
      }

      ObjectMapper objectMapper = new ObjectMapper();
      Volunteer volunteer = objectMapper.readValue(volunteerDataJson, Volunteer.class);
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

