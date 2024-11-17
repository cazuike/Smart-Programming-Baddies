package com.smartprogrammingbaddies;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.smartprogrammingbaddies.auth.ApiKey;
import com.smartprogrammingbaddies.auth.ApiKeyRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Unit tests for the Volunteer Controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class VolunteerControllerTests {
  private static final String prefix = "Volunteer enrolled with ID: ";
  @Autowired
  private MockMvc mockMvc;
  private String apiKey = TestUtils.apiKey;
  private static String volunteerId;

  @Autowired
  private ApiKeyRepository apiKeyRepository;

  /**
   * Sets up the API key before each test.
   */
  @BeforeEach
  public void setUp() {
    if (!apiKeyRepository.existsByApiKey(TestUtils.apiKey)) {
      ApiKey apiKeyEntity = new ApiKey(TestUtils.apiKey);
      apiKeyRepository.save(apiKeyEntity);
    }
  }

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  public void resetDatabase() {
    // Delete all entries from the Volunteer table
    jdbcTemplate.execute("DELETE FROM Volunteer");
    jdbcTemplate.execute("DELETE FROM Volunteer_schedule");
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

  @AfterEach
  public void logTableState() {
    System.out.println("Volunteer Table:");
    jdbcTemplate.query("SELECT * FROM Volunteer", (rs, rowNum) -> {
      System.out.println("Volunteer ID: " + rs.getString("volunteerId") +
              ", Name: " + rs.getString("name"));
      return null;
    });

    System.out.println("Volunteer_schedule Table:");
    jdbcTemplate.query("SELECT * FROM Volunteer_schedule", (rs, rowNum) -> {
      System.out.println("Volunteer ID: " + rs.getString("Volunteer_volunteerId") +
              ", Key: " + rs.getString("schedule_KEY") +
              ", Value: " + rs.getString("schedule"));
      return null;
    });
  }
}

