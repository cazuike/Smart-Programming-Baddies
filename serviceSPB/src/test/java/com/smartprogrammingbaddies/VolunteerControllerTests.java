package com.smartprogrammingbaddies;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Unit tests for the Volunteer Controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class VolunteerControllerTests {
  private static final String prefix = "Enrolled Volunteer ID:";
  @Autowired
  private MockMvc mockMvc;
  private String apiKey = TestUtils.apiKey;
  private static String volunteerId;

  @Autowired
  private com.smartprogrammingbaddies.ApiKeyRepository apiKeyRepository;

  @BeforeEach
  public void setUp() {
    if (!apiKeyRepository.existsByApiKey(TestUtils.apiKey)) {
      com.smartprogrammingbaddies.ApiKey apiKeyEntity = new com.smartprogrammingbaddies.ApiKey(TestUtils.apiKey);
      apiKeyRepository.save(apiKeyEntity);
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

  @Test
  public void updateNameTest() throws Exception {
    if (volunteerId == null) {
      enrollVolunteerTest();
    }
    mockMvc.perform(patch("/updateName")
                    .param("apiKey", apiKey)
                    .param("name","Johnny")
                    .param("volunteerId", volunteerId))
            .andExpect(status().isOk());
  }

  @Test
  public void updateRoleTest() throws Exception {
    if (volunteerId == null) {
      enrollVolunteerTest();
    }
    mockMvc.perform(patch("/updateRole")
                    .param("apiKey", apiKey)
                    .param("role","Not-a-Tester")
                    .param("volunteerId", volunteerId))
            .andExpect(status().isOk());
  }

  @Test
  public void updateScheduleTest() throws Exception {
    JSONObject testVolunteerSchedule = new JSONObject();
    testVolunteerSchedule.put("10-30-2024", "11 AM - 3 PM");
    if (volunteerId == null) {
      enrollVolunteerTest();
    }
    mockMvc.perform(patch("/updateSchedule")
                    .param("apiKey", apiKey)
                    .param("volunteerId", volunteerId)
                    .content(testVolunteerSchedule.toString())
                    .contentType("application/json"))
            .andExpect(status().isOk());
  }

  @Test
  public void confirmDeleteTest() throws Exception {
    mockMvc.perform(delete("/removeVolunteer")
                    .param("apiKey", apiKey)
                    .param("volunteerId", String.valueOf(999)))
            .andExpect(status().isNotFound());
  }

  @Test
  public void getInfoTest() throws Exception {
    if (volunteerId == null) {
      enrollVolunteerTest();
    }
    mockMvc.perform(get("/getVolunteerInfo")
                    .param("apiKey", apiKey)
                    .param("volunteerId", volunteerId))
            .andExpect(status().isOk())
            .andReturn();
  }
}

