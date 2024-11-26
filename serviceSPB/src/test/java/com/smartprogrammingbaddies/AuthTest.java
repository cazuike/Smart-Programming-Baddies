package com.smartprogrammingbaddies;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.smartprogrammingbaddies.auth.ApiKey;
import com.smartprogrammingbaddies.auth.ApiKeyRepository;
import com.smartprogrammingbaddies.auth.AuthController;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

/**
 * This class contains unit tests for Auth Controller.
 *
 * <p> Unit tests involve setting up an environment for testing and conducting the
 * necessary tests to ensure functionality. </p>
 */
@ActiveProfiles("test")
@WebMvcTest(AuthController.class)
public class AuthTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ApiKeyRepository apiKeyRepository;
  @MockBean
  private ApiKey apiKey;
  private String key;

  /**
   * Sets up the API key before each test.
   *
   * @throws Exception if an error occurs during setup.
   */
  @BeforeEach
  public void setUp() throws Exception {
    key = UUID.randomUUID().toString();
    apiKey = new ApiKey(key);
    when(apiKeyRepository.existsByApiKey(key)).thenReturn(true);
    when(apiKeyRepository.save(apiKey)).thenReturn(apiKey);
  }

  /**
   * Tests the generation of an API key.
   */
  @Test
  public void generateApiKeyTest() throws Exception {
    ResultActions result = mockMvc.perform(get("/generateApiKey"));
    result.andExpect(status().isOk());
  }

  /**
   * Tests the generatedApiKey with internal error.
   */
  @Test
  public void generateApiKeyInternalErrorTest() throws Exception {
    when(apiKeyRepository.save(any())).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(get("/generateApiKey"));
    result.andExpect(status().isInternalServerError());
  }

  /**
   * Tests the verification of an API key.
   */
  @Test
  public void verifyApiKeyTest() throws Exception {
    ResultActions result = mockMvc.perform(get("/verifyApiKey").param("apiKey", key));
    result.andExpect(status().isOk());
  }

  /**
   * Tests the verification of an invalid API key.
   */
  @Test
  public void verifyInvalidApiKeyTest() throws Exception {
    String invalidKey = UUID.randomUUID().toString();
    when(apiKeyRepository.existsByApiKey(invalidKey)).thenReturn(false);
    ResultActions result = mockMvc.perform(get("/verifyApiKey")
            .param("apiKey", invalidKey));
    result.andExpect(status().isForbidden());
  }

  /**
   * Tests the verification of an API key with internal error.
   */
  @Test
  public void verifyApiKeyInternalErrorTest() throws Exception {
    String badKey = "badKey";
    when(apiKeyRepository.existsByApiKey(badKey)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(get("/verifyApiKey").param("apiKey", badKey));
    result.andExpect(status().isInternalServerError());
  }
}
