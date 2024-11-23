package com.smartprogrammingbaddies;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.smartprogrammingbaddies.auth.ApiKey;
import com.smartprogrammingbaddies.auth.ApiKeyRepository;
import com.smartprogrammingbaddies.auth.AuthController;
import com.smartprogrammingbaddies.volunteer.Volunteer;
import com.smartprogrammingbaddies.volunteer.VolunteerController;
import com.smartprogrammingbaddies.volunteer.VolunteerRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Optional;

/**
 * Unit tests for the Volunteer Controller.
 */
@ActiveProfiles("test")
@WebMvcTest(VolunteerController.class)
public class VolunteerControllerTests {
  private static final String prefix = "Volunteer enrolled with ID: ";
  @Autowired
  private MockMvc mockMvc;
  private String apiKey = TestUtils.apiKey;
  private String badApiKey = TestUtils.badApiKey;
  private static String volunteerId = String.valueOf(0);

  @MockBean
  private AuthController auth;

  @MockBean
  private ApiKeyRepository apiKeyRepository;

  @MockBean
  private VolunteerRepository volunteerRepository;

  /**
   * Sets up the API key before each test.
   */
  @BeforeEach
  public void setUp() {

    ResponseEntity<?> mockResponse = new ResponseEntity<>("Valid API Key", HttpStatus.OK);
    ResponseEntity<?> mockResponse2 = new ResponseEntity<>("Invalid API key.", HttpStatus.FORBIDDEN);

    Mockito.when(auth.verifyApiKey(apiKey)).thenReturn((ResponseEntity) mockResponse);
    Mockito.when(auth.verifyApiKey(badApiKey)).thenReturn((ResponseEntity) mockResponse2);

    Volunteer mockVolunteer = new Volunteer("John Doe", "Tester", "1234567890", new HashMap<>());
    Mockito.when(volunteerRepository.findById(Integer.valueOf(volunteerId)))
            .thenReturn(Optional.of(mockVolunteer));
  }

  @Test
  public void enrollVolunteerTest() throws Exception {
    Mockito.when(volunteerRepository.save(Mockito.any(Volunteer.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

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
  }

  @Test
  public void enrollVolunteerBadKeyTest() throws Exception {
    Mockito.when(volunteerRepository.save(Mockito.any(Volunteer.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

    JSONObject testVolunteerSchedule = new JSONObject();
    testVolunteerSchedule.put("10-30-2024", "9 AM - 12 PM");

    MvcResult result = mockMvc.perform(patch("/enrollVolunteer")
                    .param("apiKey", badApiKey)
                    .param("name", "John Doe")
                    .param("role", "Tester")
                    .content(testVolunteerSchedule.toString())
                    .contentType("application/json"))
            .andExpect(status().isForbidden())
            .andReturn();
  }

  @Test
  public void updateNameTest() throws Exception {
    mockMvc.perform(patch("/updateName")
                    .param("apiKey", apiKey)
                    .param("name", "Johnny")
                    .param("volunteerId", volunteerId))
            .andExpect(status().isOk());
  }

  @Test
  public void updateNameBadKeyTest() throws Exception {
    mockMvc.perform(patch("/updateName")
                    .param("apiKey", badApiKey)
                    .param("name", "Johnny")
                    .param("volunteerId", volunteerId))
            .andExpect(status().isForbidden());
  }

  @Test
  public void updateRoleTest() throws Exception {
    mockMvc.perform(patch("/updateRole")
                    .param("apiKey", apiKey)
                    .param("role", "Not-a-Tester")
                    .param("volunteerId", volunteerId))
            .andExpect(status().isOk());
  }

  @Test
  public void updateRoleBadKeyTest() throws Exception {
    mockMvc.perform(patch("/updateRole")
                    .param("apiKey", badApiKey)
                    .param("role", "Not-a-Tester")
                    .param("volunteerId", volunteerId))
            .andExpect(status().isForbidden());
  }

  @Test
  public void updateScheduleTest() throws Exception {
    JSONObject testVolunteerSchedule = new JSONObject();
    testVolunteerSchedule.put("10-30-2024", "11 AM - 3 PM");
    mockMvc.perform(patch("/updateSchedule")
                    .param("apiKey", apiKey)
                    .param("volunteerId", volunteerId)
                    .content(testVolunteerSchedule.toString())
                    .contentType("application/json"))
            .andExpect(status().isOk());
  }

  @Test
  public void updateScheduleBadKeyTest() throws Exception {
    JSONObject testVolunteerSchedule = new JSONObject();
    testVolunteerSchedule.put("10-30-2024", "11 AM - 3 PM");
    mockMvc.perform(patch("/updateSchedule")
                    .param("apiKey", badApiKey)
                    .param("volunteerId", volunteerId)
                    .content(testVolunteerSchedule.toString())
                    .contentType("application/json"))
            .andExpect(status().isForbidden());
  }

  @Test
  public void confirmNoDeleteTest() throws Exception {
    mockMvc.perform(delete("/removeVolunteer")
                    .param("apiKey", apiKey)
                    .param("volunteerId", String.valueOf(999)))
            .andExpect(status().isNotFound());
  }

  @Test
  public void confirmNoDeleteBadKeyTest() throws Exception {
    mockMvc.perform(delete("/removeVolunteer")
                    .param("apiKey", badApiKey)
                    .param("volunteerId", String.valueOf(999)))
            .andExpect(status().isForbidden());
  }

  @Test
  public void confirmDeleteTest() throws Exception {
    Mockito.when(volunteerRepository.existsById(Integer.valueOf(volunteerId))).thenReturn(true);
    mockMvc.perform(delete("/removeVolunteer")
                    .param("apiKey", apiKey)
                    .param("volunteerId", volunteerId))
            .andExpect(status().isOk());
  }

  @Test
  public void confirmDeleteBadKeyTest() throws Exception {
    mockMvc.perform(delete("/removeVolunteer")
                    .param("apiKey", badApiKey)
                    .param("volunteerId", volunteerId))
            .andExpect(status().isForbidden());
  }

  @Test
  public void getInfoTest() throws Exception {
    mockMvc.perform(get("/getVolunteerInfo")
                    .param("apiKey", apiKey)
                    .param("volunteerId", volunteerId))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  public void getInfoBadKeyTest() throws Exception {
    mockMvc.perform(get("/getVolunteerInfo")
                    .param("apiKey", badApiKey)
                    .param("volunteerId", volunteerId))
            .andExpect(status().isForbidden())
            .andReturn();
  }
}