package com.smartprogrammingbaddies;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Unit tests for the Volunteer Controller.
 */
@Sql(statements = "INSERT INTO api_keys (api_key) VALUES ('test-service-key');")
@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.profiles.active=test")
@ActiveProfiles("test")
public class VolunteerControllerTests {
  private static final String prefix = "Enrolled Volunteer ID:";
  @Autowired
  private MockMvc mockMvc;
  private String apiKey = TestUtils.apiKey;
  private static String volunteerId;

  @Autowired
  private DataSource dataSource;
  @BeforeEach
  public void setUp() throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement clearStatement = connection.prepareStatement("DELETE FROM api_keys");
         PreparedStatement insertStatement = connection.prepareStatement(
                 "INSERT INTO api_keys (api_key) VALUES ('test-service-key')")) {

      clearStatement.executeUpdate();
      insertStatement.executeUpdate();
    }
  }

  @Test
    public void enrollVolunteerTest() throws Exception {
    JSONObject testVolunteerSchedule = new JSONObject();
    testVolunteerSchedule.put("10-30-2024", "9 AM - 12 PM");

    MvcResult result = mockMvc.perform(patch("/enrollVolunteer")
                        .param("apiKey", apiKey)
                        .param("name", "John Doe")
                        .param("role", "Tester")
                        .content(testVolunteerSchedule.toString())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    volunteerId = TestUtils.extract(prefix, responseContent);
  }


}

