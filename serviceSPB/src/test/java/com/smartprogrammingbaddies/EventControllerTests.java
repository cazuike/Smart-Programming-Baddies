package com.smartprogrammingbaddies;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Unit tests for the Event Controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {
  @Autowired
  private MockMvc mockMvc;
  private String apiKey = TestUtils.apiKey;
  private static String eventId;
  private static final String prefix = "Event ID: ";

  @Test
  public void createEventTest() throws Exception {
    MvcResult result = mockMvc.perform(post("/createEvent")
        .param("apiKey", apiKey)
        .param("name", "Food Drive")
        .param("description", "A food drive for the local community").param("date", "10-30-2024")
        .param("time", "9 AM - 12 PM")
        .param("location", "123 Main St")
        .param("organizer", "Columbia University")
        .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    eventId = TestUtils.extract(prefix, responseContent);
  }

  @Test
  public void createEventFailTest() throws Exception {
    MvcResult result = mockMvc.perform(post("/createEvent")
        .param("apiKey", "incorrectApiKey")
        .param("name", "Food Drive")
        .param("description", "A food drive for the local community")
        .param("date", "10-30-2024")
        .param("time", "9 AM - 12 PM")
        .param("location", "123 Main St")
        .param("organizer", "Columbia University")
        .contentType("application/json"))
            .andExpect(status().is4xxClientError())
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    eventId = TestUtils.extract(prefix, responseContent);
  }

  @Test
  public void createAndRetrieveEventTest() throws Exception {
    MvcResult result = mockMvc.perform(post("/createEvent")
          .param("apiKey", apiKey)
          .param("name", "Food Drive")
          .param("description", "A food drive for the local community").param("date", "10-30-2024")
          .param("time", "9 AM - 12 PM")
          .param("location", "123 Main St")
          .param("organizer", "Columbia University")
          .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    eventId = TestUtils.extract(prefix, responseContent);

    result = mockMvc.perform(get("/retrieveEvent")
          .param("apiKey", apiKey)
          .param("eventId", eventId))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  public void retrieveEventFailTest() throws Exception {
    mockMvc.perform(get("/retrieveEvent")
          .param("apiKey", "IncorrectApiKey")
          .param("eventId", eventId))
  .andExpect(status().is4xxClientError())
            .andReturn();
  }

  @Test
  public void removeEventFailTest() throws Exception {
    mockMvc.perform(delete("/removeEvent")
            .param("apiKey", "IncorrectApiKey")
            .param("eventId", eventId))
            .andExpect(status().is4xxClientError())
            .andReturn();
  }

  /**
   * Cleanup after each test.
   *
   * @throws Exception An exception if anything goes wrong with the cleanup.
   */
  @AfterEach
  public void cleanup() throws Exception {
    if (eventId != null) {
      mockMvc.perform(delete("/removeEvent")
            .param("apiKey", apiKey)
            .param("eventId", eventId));
      eventId = null;
    }
  }
}

