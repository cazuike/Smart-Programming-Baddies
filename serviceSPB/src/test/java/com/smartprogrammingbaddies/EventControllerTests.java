package com.smartprogrammingbaddies;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.smartprogrammingbaddies.auth.ApiKeyRepository;
import com.smartprogrammingbaddies.auth.AuthController;
import com.smartprogrammingbaddies.event.Event;
import com.smartprogrammingbaddies.event.EventController;
import com.smartprogrammingbaddies.event.EventRepository;
import com.smartprogrammingbaddies.organization.Organization;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import com.smartprogrammingbaddies.storagecenter.StorageCenterRepository;
import com.smartprogrammingbaddies.utils.TimeSlot;
import com.smartprogrammingbaddies.volunteer.Volunteer;
import com.smartprogrammingbaddies.volunteer.VolunteerRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

  /**
   * Sets up the Event instance and related objects before each test.
   */
  @BeforeEach
  public void setUp() {
    Mockito.when(eventRepository.findById(Integer.valueOf(eventId))).thenReturn(
        Optional.of(new Event()));
    ResponseEntity<?> validKeyResponse = new ResponseEntity<>("Valid API Key",
        HttpStatus.OK);
    ResponseEntity<?> invalidKeyResponse = new ResponseEntity<>("Invalid API key.",
        HttpStatus.FORBIDDEN);

    Mockito.when(auth.verifyApiKey(apiKey)).thenReturn((ResponseEntity) validKeyResponse);
    Mockito.when(auth.verifyApiKey(badApiKey)).thenReturn((ResponseEntity) invalidKeyResponse);

    StorageCenter mockStorage = new StorageCenter("Mock Storage", "A mock storage for testing");
    Set<String> mockSchedule = new HashSet<>();
    mockSchedule.add("10-17-2024 10:00 AM");
    Organization mockOrg = new Organization("Mock Org", "Test",
        mockSchedule, null);

    Event mockEvent = new Event("Food Drive", "A food drive for the local community",
        "07-11-2021", new TimeSlot("09:00", "13:30"), "Columbia University",
        mockStorage, mockOrg, new HashSet<>());

    // Mock repository behavior
    Mockito.when(eventRepository.findById(1)).thenReturn(Optional.of(mockEvent));
    Mockito.when(eventRepository.save(Mockito.any(Event.class)))
        .thenAnswer(invocation -> {
          Event event = invocation.getArgument(0);
          return event;
        });

    Mockito.when(eventRepository.findById(Integer.valueOf(eventId)))
        .thenReturn(Optional.of(mockEvent));
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
  public void createEvenStorageCenterNotFoundTest() throws Exception {
    Mockito.when(storageCenterRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
    mockMvc.perform(post("/createEvent")
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
        .andExpect(status().isNotFound());
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
    mockMvc.perform(get("/retrieveEvent")
        .param("apiKey", apiKey)
        .param("eventId", eventId))
        .andExpect(status().isOk());
  }

  @Test
  public void addVolunteerToEventTest() throws Exception {
    Mockito.when(volunteerRepository.findById(1)).thenReturn(Optional.of(new Volunteer()));

    mockMvc.perform(post("/addVolunteerToEvent")
        .param("apiKey", apiKey)
        .param("eventId", "1")
        .param("volunteerId", "1")
        .contentType("application/json"))
        .andExpect(status().isOk());
  }

  @Test
  public void addVolunteerToEventFailTest() throws Exception {
    mockMvc.perform(post("/addVolunteerToEvent")
        .param("apiKey", badApiKey)
        .param("eventId", "2")
        .param("volunteerId", "1")
        .contentType("application/json"))
        .andExpect(status().isUnauthorized());

    Mockito.when(eventRepository.findById(2)).thenReturn(Optional.empty());

    mockMvc.perform(post("/addVolunteerToEvent")
        .param("apiKey", apiKey)
        .param("eventId", "2")
        .param("volunteerId", "1")
        .contentType("application/json"))
        .andExpect(status().isNotFound());

    Mockito.when(eventRepository.findById(1)).thenReturn(Optional.of(new Event()));
    Mockito.when(volunteerRepository.findById(3)).thenReturn(Optional.empty());

    mockMvc.perform(post("/addVolunteerToEvent")
        .param("apiKey", apiKey)
        .param("eventId", "1")
        .param("volunteerId", "3")
        .contentType("application/json"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void listEventsTest() throws Exception {
    mockMvc.perform(get("/listEvents")
        .param("apiKey", apiKey)
        .contentType("application/json"))
        .andExpect(status().isOk());
  }

  @Test
  public void listEventsFailTest() throws Exception {
    mockMvc.perform(get("/listEvents")
        .param("apiKey", badApiKey)
        .contentType("application/json"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void retrieveEventFailTest() throws Exception {

    mockMvc.perform(get("/retrieveEvent")
        .param("apiKey", apiKey)
        .param("eventId", "9"))
        .andExpect(status().isNotFound());

    mockMvc.perform(get("/retrieveEvent")
        .param("apiKey", badApiKey)
        .param("eventId", eventId))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void searchEventsByDateTest() throws Exception {
    List<Event> mockEvents = new ArrayList<>();
    mockEvents.add(new Event("Food Drive", "Description", "2024-10-30",
        new TimeSlot("09:00", "13:30"), "Location", null, null, new HashSet<>()));

    Mockito.when(eventRepository.findByDate("2024-10-30")).thenReturn(mockEvents);

    mockMvc.perform(get("/searchEventsByDate")
        .param("apiKey", apiKey)
        .param("date", "2024-10-30")
        .contentType("application/json"))
        .andExpect(status().isOk());
  }

  @Test
  public void searchEventsByDateFailTest() throws Exception {
    List<Event> mockEvents = new ArrayList<>();
    Mockito.when(eventRepository.findByDate("2024-11-01")).thenReturn(mockEvents);

    mockMvc.perform(get("/searchEventsByDate")
        .param("apiKey", apiKey)
        .param("date", "2024-11-01")
        .contentType("application/json"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void searchEventsByLocationTest() throws Exception {
    List<Event> mockEvents = new ArrayList<>();
    mockEvents.add(new Event("Food Drive", "Description", "2024-10-30",
        new TimeSlot("09:00", "13:30"), "Columbia University", null, null, new HashSet<>()));

    Mockito.when(eventRepository.findByLocationContainingIgnoreCase("Columbia University")).thenReturn(mockEvents);

    mockMvc.perform(get("/searchEventsByLocation")
        .param("apiKey", apiKey)
        .param("location", "Columbia University")
        .contentType("application/json"))
        .andExpect(status().isOk());
  }

  @Test
  public void searchEventsByLocationFailTest() throws Exception {
    List<Event> mockEvents = new ArrayList<>();
    Mockito.when(eventRepository.findByLocationContainingIgnoreCase("Nonexistent Location")).thenReturn(mockEvents);

    mockMvc.perform(get("/searchEventsByLocation")
        .param("apiKey", apiKey)
        .param("location", "Nonexistent Location")
        .contentType("application/json"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void removeEventTest() throws Exception {
    Mockito.when(eventRepository.existsById(Integer.parseInt(eventId))).thenReturn(true);
    Mockito.doNothing().when(eventRepository).deleteById(Integer.parseInt(eventId));
    mockMvc.perform(delete("/removeEvent")
        .param("apiKey", apiKey)
        .param("eventId", "0"))
        .andExpect(status().isOk());
  }

  @Test
  public void removeEventFailTest() throws Exception {
    mockMvc.perform(delete("/removeEvent")
        .param("apiKey", badApiKey)
        .param("eventId", eventId))
        .andExpect(status().isUnauthorized());

    mockMvc.perform(delete("/removeEvent")
        .param("apiKey", apiKey)
        .param("eventId", "9"))
        .andExpect(status().isNotFound());

    mockMvc.perform(delete("/removeEvent")
        .param("apiKey", apiKey)
        .param("eventId", "invalid"))
        .andExpect(status().isBadRequest());
  }
}
