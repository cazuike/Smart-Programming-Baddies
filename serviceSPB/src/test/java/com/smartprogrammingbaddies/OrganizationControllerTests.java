package com.smartprogrammingbaddies;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.smartprogrammingbaddies.client.Client;
import com.smartprogrammingbaddies.client.ClientRepository;
import com.smartprogrammingbaddies.event.Event;
import com.smartprogrammingbaddies.event.EventRepository;
import com.smartprogrammingbaddies.organization.Organization;
import com.smartprogrammingbaddies.organization.OrganizationController;
import com.smartprogrammingbaddies.organization.OrganizationRepository;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import com.smartprogrammingbaddies.storagecenter.StorageCenterRepository;
import com.smartprogrammingbaddies.utils.TimeSlot;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

/**
 * API endpoints tests for the OrganizationController class.
 */
@ActiveProfiles("test")
@WebMvcTest(OrganizationController.class)
public class OrganizationControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrganizationRepository organizationRepository;

  @MockBean
  private ClientRepository clientRepository;

  @MockBean
  private EventRepository eventRepository;

  @MockBean
  private StorageCenterRepository storageCenterRepository;

  @MockBean
  private StorageCenter storage;

  @MockBean
  private Set<Event> events;

  @MockBean
  private Client client;

  @MockBean
  private Organization organization;

  @MockBean
  private TimeSlot timeSlot;

  /**
    * Sets up the tests objects and mocked repository actions.
    *
    * @throws Exception if an error occurs.
    */
  @BeforeEach
  public void setUp() throws Exception {
    storage = new StorageCenter("UpperBestSide", "For Profit");
    storage.setDatabaseId(0);
    when(storageCenterRepository.save(storage)).thenReturn(storage);
    when(storageCenterRepository.findById(0)).thenReturn(Optional.of(storage));
    when(storageCenterRepository.findById(1)).thenReturn(Optional.empty());
    client = new Client("test");
    organization = new Organization("Upper Best Side", "For Profit", client);
    client.setId(0);
    client.setOrganization(organization);
    organization.setDatabaseId(0);
    organization.setStorage(storage);
    when(clientRepository.save(any(Client.class))).thenReturn(client);
    when(clientRepository.findByApiKey("test")).thenReturn(client);
    when(clientRepository.findByApiKey("invalid")).thenReturn(null);
    when(organizationRepository.save(organization)).thenReturn(organization);
    when(organizationRepository.findById(0)).thenReturn(Optional.of(organization));
    when(organizationRepository.findById(1)).thenReturn(Optional.empty());

    events = new HashSet<>();
    timeSlot = new TimeSlot("09:00", "10:00");
    Event event = new Event(
        "Charity Drive",
        "A community charity event",
        "2024-12-25",
        timeSlot,
        "East Village",
        storage,
        organization,
        new HashSet<>());
    events.add(event);

    when(eventRepository.save(event)).thenReturn(event);
    when(eventRepository.findById(0)).thenReturn(Optional.of(event));
    when(eventRepository.findById(1)).thenReturn(Optional.empty());
    when(eventRepository.findAll()).thenReturn(events);
  }

  /**
    * Tests the createOrganization method.
    */
  @Test
  public void createOrganizationTest() throws Exception {
    ResultActions result = mockMvc.perform(post("/createOrganization")
          .param("orgName", "Upper Best Side")
          .param("orgType", "For Profit")
          .param("apiKey", "test"));

    result.andExpect(status().isOk());
  }

  /**
    * Tests the createOrganization method with invalid inputs.
    */
  @Test
  public void createOrganizationInvalidTest() throws Exception {
    ResultActions result = mockMvc.perform(post("/createOrganization")
          .param("orgName", "")
          .param("orgType", "For Profit")
          .param("apiKey", "test"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(post("/createOrganization")
          .param("orgName", "Upper Best Side")
          .param("orgType", "")
          .param("apiKey", "test"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(post("/createOrganization")
          .param("orgName", "Upper Best Side")
          .param("orgType", "For Profit")
          .param("apiKey", ""));
    result.andExpect(status().isForbidden());

    result = mockMvc.perform(post("/createOrganization")
          .param("orgName", "Upper Best Side")
          .param("orgType", "For Profit")
          .param("apiKey", "invalid"));
    result.andExpect(status().isForbidden());
  }

  /**
    * Tests the getOrganization method.
    */
  @Test
  public void getOrganizationTest() throws Exception {
    ResultActions result = mockMvc.perform(get("/getOrganization")
          .param("orgId", "0")
          .param("apiKey", "test"));
    result.andExpect(status().isOk());
  }

  /**
   * Tests the getOrganization method with subscription status change.
   */
  @Test
  public void getOrganizationSubscriptionStatusChangeTest() throws Exception {
    organization.changeSubscriptionStatus();
    ResultActions result = mockMvc.perform(get("/getOrganization")
          .param("orgId", "0")
          .param("apiKey", "test"));
    result.andExpect(status().isOk());
  }

  /**
    * Tests the getOrganization method with invalid inputs.
    */
  @Test
  public void getOrganizationInvalidTest() throws Exception {
    ResultActions result = mockMvc.perform(get("/getOrganization")
          .param("orgId", "1")
          .param("apiKey", "test"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(get("/getOrganization")
          .param("orgId", "0")
          .param("apiKey", ""));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(get("/getOrganization")
          .param("orgId", "0")
          .param("apiKey", "invalid"));
    result.andExpect(status().isBadRequest());
  }

  /**
    * Tests the changeSubscriptionStatus method.
    */
  @Test
  public void changeSubscriptionStatusTest() throws Exception {
    ResultActions result = mockMvc.perform(patch("/changeSubscriptionStatus")
          .param("orgId", "0")
          .param("apiKey", "test"));

    result.andExpect(status().isOk());

    result = mockMvc.perform(patch("/changeSubscriptionStatus")
          .param("orgId", "0")
          .param("apiKey", "test"));
    result.andExpect(status().isOk());
  }

  /**
    * Tests the changeSubscriptionStatus method with invalid inputs.
    */
  @Test
  public void changeSubscriptionStatusInvalidTest() throws Exception {
    ResultActions result = mockMvc.perform(patch("/changeSubscriptionStatus")
          .param("orgId", "1")
          .param("apiKey", "test"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(patch("/changeSubscriptionStatus")
          .param("orgId", "0")
          .param("apiKey", " "));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(patch("/changeSubscriptionStatus")
          .param("orgId", "0")
          .param("apiKey", "invalid"));
    result.andExpect(status().isBadRequest());
  }

  /**
    * Tests the deleteOrganization method.
    */
  @Test
  public void deleteOrganizationTest() throws Exception {
    ResultActions result = mockMvc.perform(delete("/deleteOrganization")
          .param("orgId", "0")
          .param("apiKey", "test"));

    result.andExpect(status().isOk());
  }

  /**
    * Tests the deleteOrganization method class with invalid inputs.
    */
  @Test
  public void deleteOrganizationInvalidTest() throws Exception {
    ResultActions result = mockMvc.perform(delete("/deleteOrganization")
          .param("orgId", "1")
          .param("apiKey", "test"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(delete("/deleteOrganization")
          .param("orgId", "0")
          .param("apiKey", " "));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(delete("/deleteOrganization")
          .param("orgId", "0")
          .param("apiKey", "invalid"));
    result.andExpect(status().isBadRequest());
  }

  /**
    * Tests the registerEvent method.
    */
  @Test
  public void registerEventTest() throws Exception {
    Event event = new Event(
        "Charity Drive",
        "A community charity event",
        "2024-12-25",
        timeSlot,
        "East Village",
        storage,
        null,
        new HashSet<>());
    when(organizationRepository.findById(0)).thenReturn(Optional.of(organization));
    when(eventRepository.findById(0)).thenReturn(Optional.of(event));
    ResultActions result = mockMvc.perform(post("/registerEvent")
          .param("orgId", "0")
          .param("apiKey", "test")
          .param("eventId", "0"));
    result.andExpect(status().isOk());
  }

  /**
    * Tests the registerEvent method with invalid inputs.
    */
  @Test
  public void registerEventInvalidTest() throws Exception {
    ResultActions result = mockMvc.perform(post("/registerEvent")
          .param("orgId", "1")
          .param("apiKey", "test")
          .param("eventId", "0"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(post("/registerEvent")
          .param("orgId", "0")
          .param("apiKey", " ")
          .param("eventId", "0"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(post("/registerEvent")
          .param("orgId", "0")
          .param("apiKey", "invalid")
          .param("eventId", "0"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(post("/registerEvent")
          .param("orgId", "0")
          .param("apiKey", "test")
          .param("eventId", " "));
    result.andExpect(status().isBadRequest());
  }

  /**
    * Tests the unregisterEvent method.
    */
  @Test
  public void unregisterEventTest() throws Exception {
    Event event = new Event(
        "Charity Drive",
        "A community charity event",
        "2024-12-25",
        null,
        "East Village",
        storage,
        organization,
        new HashSet<>());
    when(eventRepository.findById(0)).thenReturn(Optional.of(event));
    ResultActions result = mockMvc.perform(delete("/unregisterEvent")
          .param("orgId", "0")
          .param("apiKey", "test")
          .param("eventId", "0"));
    result.andExpect(status().isOk());
  }

  /**
    * Tests the unregisterEvent method with invalid inputs.
    */
  @Test
  public void unregisterEventInvalidTest() throws Exception {
    ResultActions result = mockMvc.perform(delete("/unregisterEvent")
          .param("orgId", "1")
          .param("apiKey", "test")
          .param("eventId", "0"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(delete("/unregisterEvent")
          .param("orgId", "0")
          .param("apiKey", " ")
          .param("eventId", "0"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(delete("/unregisterEvent")
          .param("orgId", "0")
          .param("apiKey", "invalid")
          .param("eventId", "0"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(delete("/unregisterEvent")
          .param("orgId", "0")
          .param("apiKey", "test")
          .param("eventId", " "));
    result.andExpect(status().isBadRequest());
  }

  /**
    * Tests the listOrganizationEvents method.
    */
  @Test
  public void listOrganizationEventsTest() throws Exception {
    ResultActions result = mockMvc.perform(get("/listOrganizationEvents")
          .param("orgId", "0")
          .param("apiKey", "test"));
    result.andExpect(status().isOk());
  }

  /**
    * Tests the listOrganizationEvents method with invalid inputs.
    */
  @Test
  public void listOrganizationEventsInvalidTest() throws Exception {
    ResultActions result = mockMvc.perform(get("/listOrganizationEvents")
          .param("orgId", "1")
          .param("apiKey", "test"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(get("/listOrganizationEvents")
          .param("orgId", "0")
          .param("apiKey", " "));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(get("/listOrganizationEvents")
          .param("orgId", "0")
          .param("apiKey", "invalid"));
    result.andExpect(status().isBadRequest());
  }

  /**
    * Tests the linkStorageCenter method.
    */
  @Test
  public void linkStorageCenterTest() throws Exception {
    ResultActions result = mockMvc.perform(post("/linkStorageCenter")
          .param("orgId", "0")
          .param("apiKey", "test")
          .param("storageId", "0"));
    result.andExpect(status().isOk());
  }

  /**
    * Tests the linkStorageCenter method with invalid inputs.
    */
  @Test
  public void linkStorageCenterInvalidTest() throws Exception {
    ResultActions result = mockMvc.perform(post("/linkStorageCenter")
          .param("orgId", "1")
          .param("apiKey", "test")
          .param("storageId", "0"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(post("/linkStorageCenter")
          .param("orgId", "0")
          .param("apiKey", " ")
          .param("storageId", "0"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(post("/linkStorageCenter")
          .param("orgId", "0")
          .param("apiKey", "invalid")
          .param("storageId", "0"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(post("/linkStorageCenter")
          .param("orgId", "0")
          .param("apiKey", "test")
          .param("storageId", " "));
    result.andExpect(status().isBadRequest());
  }

  /**
    * Tests the getStorageCenter method.
    */
  @Test
  public void getStorageCenterTest() throws Exception {
    ResultActions result = mockMvc.perform(get("/getStorageCenter")
          .param("orgId", "0")
          .param("apiKey", "test"));
    result.andExpect(status().isOk());
  }

  /**
    * Tests the getStorageCenter method with invalid inputs.
    */
  @Test
  public void getStorageCenterInvalidTest() throws Exception {
    ResultActions result = mockMvc.perform(get("/getStorageCenter")
          .param("orgId", "1")
          .param("apiKey", "test"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(get("/getStorageCenter")
          .param("orgId", "0")
          .param("apiKey", " "));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(get("/getStorageCenter")
          .param("orgId", "0")
          .param("apiKey", "invalid"));
    result.andExpect(status().isBadRequest());
  }

  /**
    * Tests the createOrganization method with internal server error.
    */
  @Test
  public void createOrganizationInternalServerErrorTest() throws Exception {
    when(organizationRepository.save(any(Organization.class))).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(post("/createOrganization")
          .param("orgName", "Upper Best Side")
          .param("orgType", "For Profit")
          .param("apiKey", "test"));
    result.andExpect(status().isInternalServerError());
  }

  /**
    * Tests the getOrganization method with internal server error.
    */
  @Test
  public void getOrganizationInternalServerErrorTest() throws Exception {
    when(organizationRepository.findById(0)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(get("/getOrganization")
          .param("orgId", "0")
          .param("apiKey", "test"));
    result.andExpect(status().isInternalServerError());
  }

  /**
    * Tests the changeSubscriptionStatus method with internal server error.
    */
  @Test
  public void changeSubscriptionStatusInternalServerErrorTest() throws Exception {
    when(organizationRepository.findById(0)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(patch("/changeSubscriptionStatus")
          .param("orgId", "0")
          .param("apiKey", "test"));
    result.andExpect(status().isInternalServerError());
  }

  /**
    * Tests the deleteOrganization method with internal server error.
    */
  @Test
  public void deleteOrganizationInternalServerErrorTest() throws Exception {
    when(clientRepository.findByApiKey("test")).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(delete("/deleteOrganization")
          .param("orgId", "0")
          .param("apiKey", "test"));
    result.andExpect(status().isInternalServerError());
  }

  /**
    * Tests the registerEvent method with internal server error.
    */
  @Test
  public void registerEventInternalServerErrorTest() throws Exception {
    when(organizationRepository.findById(0)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(post("/registerEvent")
          .param("orgId", "0")
          .param("apiKey", "test")
          .param("eventId", "0"));
    result.andExpect(status().isInternalServerError());
  }

  /**
    * Tests the unregisterEvent method with internal server error.
    */
  @Test
  public void unregisterEventInternalServerErrorTest() throws Exception {
    when(eventRepository.findById(0)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(delete("/unregisterEvent")
          .param("orgId", "0")
          .param("apiKey", "test")
          .param("eventId", "0"));
    result.andExpect(status().isInternalServerError());
  }

  /**
    * Tests the listOrganizationEvents method with internal server error.
    */
  @Test
  public void listOrganizationEventsInternalServerErrorTest() throws Exception {
    when(organizationRepository.findById(0)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(get("/listOrganizationEvents")
          .param("orgId", "0")
          .param("apiKey", "test"));
    result.andExpect(status().isInternalServerError());
  }

  /**
    * Tests the linkStorageCenter method with internal server error.
    */
  @Test
  public void linkStorageCenterInternalServerErrorTest() throws Exception {
    when(organizationRepository.findById(0)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(post("/linkStorageCenter")
          .param("orgId", "0")
          .param("apiKey", "test")
          .param("storageId", "0"));
    result.andExpect(status().isInternalServerError());
  }

  /**
    * Tests the getStorageCenter method with internal server error.
    */
  @Test
  public void getStorageCenterInternalServerErrorTest() throws Exception {
    when(organizationRepository.findById(0)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(get("/getStorageCenter")
          .param("orgId", "0")
          .param("apiKey", "test"));
    result.andExpect(status().isInternalServerError());
  }
}
