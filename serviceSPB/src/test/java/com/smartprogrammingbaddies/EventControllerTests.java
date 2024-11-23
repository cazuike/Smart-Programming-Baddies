package com.smartprogrammingbaddies;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.smartprogrammingbaddies.auth.AuthController;
import com.smartprogrammingbaddies.auth.ApiKeyRepository;
import com.smartprogrammingbaddies.event.Event;
import com.smartprogrammingbaddies.event.EventController;
import com.smartprogrammingbaddies.event.EventRepository;
import com.smartprogrammingbaddies.organization.Organization;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import com.smartprogrammingbaddies.storagecenter.StorageCenterRepository;
import com.smartprogrammingbaddies.utils.TimeSlot;
import com.smartprogrammingbaddies.volunteer.Volunteer;
import com.smartprogrammingbaddies.volunteer.VolunteerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

/**
 * Unit tests for the Event Controller.
 */
@WebMvcTest(EventController.class)
public class EventControllerTests {
  private static final String prefix = "Event was created successfully with ID: ";
  @Autowired
  private MockMvc mockMvc;

  private String apiKey = TestUtils.apiKey;
  private String badApiKey = TestUtils.badApiKey;
  private static String eventId = "0";

  @MockBean
  private AuthController auth;

  @MockBean
  private ApiKeyRepository apiKeyRepository;
  @MockBean
  private StorageCenterRepository storageCenterRepository;
  @MockBean
  private EventRepository eventRepository;
  @MockBean
  private VolunteerRepository volunteerRepository;

  @BeforeEach
  public void setUp() {
    Mockito.when(eventRepository.findById(Integer.valueOf(eventId))).thenReturn(Optional.of(new Event()));
    ResponseEntity<?> validKeyResponse = new ResponseEntity<>("Valid API Key", HttpStatus.OK);
    ResponseEntity<?> invalidKeyResponse = new ResponseEntity<>("Invalid API key.", HttpStatus.FORBIDDEN);

    Mockito.when(auth.verifyApiKey(apiKey)).thenReturn((ResponseEntity) validKeyResponse);
    Mockito.when(auth.verifyApiKey(badApiKey)).thenReturn((ResponseEntity) invalidKeyResponse);

    StorageCenter mockStorage = new StorageCenter("Mock Storage", "A mock storage for testing");
    Set<String> mockSchedule = new HashSet<>();
    mockSchedule.add("10-17-2024 10:00 AM");
    Organization mockOrg = new Organization("Mock Org", "Test", mockSchedule, null);

    Event mockEvent = new Event("Food Drive","A food drive for the local community", "07-11-2021",
            new TimeSlot("09:00", "13:30"),"Columbia University", mockStorage, mockOrg, new HashSet<>());

    // Mock repository behavior
    Mockito.when(eventRepository.findById(1)).thenReturn(Optional.of(mockEvent));
    Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenAnswer(invocation -> {
      Event event = invocation.getArgument(0);
      return event;
    });

    Mockito.when(eventRepository.findById(Integer.valueOf(eventId))).thenReturn(Optional.of(mockEvent));
  }

  @Test
  public void createEventTest() throws Exception {
    Mockito.when(storageCenterRepository.findById(0)).thenReturn(Optional.of(new StorageCenter()));
    MvcResult result = mockMvc.perform(post("/createEvent")
                    .param("apiKey", apiKey)
                    .param("name", "Food Drive")
                    .param("description", "A food drive for the local community")
                    .param("date", "10-30-2024")
                    .param("startTime", "09:00")
                    .param("endTime", "13:30")
                    .param("location", "Columbia University")
                    .param("storageCenterId", "0")
                    .param("organizationId", "0")
                    .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  public void createEventFailTest() throws Exception {
    mockMvc.perform(post("/createEvent")
                    .param("apiKey", badApiKey)
                    .param("name", "Food Drive")
                    .param("description", "A food drive for the local community")
                    .param("date", "10-30-2024")
                    .param("startTime", "09:00")
                    .param("endTime", "13:30")
                    .param("location", "Columbia University")
                    .param("storageCenterId", "0")
                    .param("organizationId", "0")
                    .contentType("application/json"))
            .andExpect(status().isUnauthorized());
  }

  @Test
  public void createAndRetrieveEventTest() throws Exception {
    Mockito.when(storageCenterRepository.findById(0)).thenReturn(Optional.of(new StorageCenter()));
    MvcResult result = mockMvc.perform(post("/createEvent")
                    .param("apiKey", apiKey)
                    .param("name", "Food Drive")
                    .param("description", "A food drive for the local community")
                    .param("date", "10-30-2024")
                    .param("startTime", "09:00")
                    .param("endTime", "13:30")
                    .param("location", "Columbia University")
                    .param("storageCenterId", "0")
                    .param("organizationId", "0")
                    .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();

//    String responseContent = result.getResponse().getContentAsString();
//    eventId = TestUtils.extract(prefix, responseContent);

    mockMvc.perform(get("/retrieveEvent")
                    .param("apiKey", apiKey)
                    .param("eventId", eventId))
            .andExpect(status().isOk());

  }

  @Test
  public void retrieveEventFailTest() throws Exception {
    mockMvc.perform(get("/retrieveEvent")
                    .param("apiKey", badApiKey)
                    .param("eventId", eventId))
            .andExpect(status().isUnauthorized());

  }

  @Test
  public void removeEventFailTest() throws Exception {
    mockMvc.perform(delete("/removeEvent")
                    .param("apiKey", badApiKey)
                    .param("eventId", eventId))
            .andExpect(status().isUnauthorized());
  }

}
