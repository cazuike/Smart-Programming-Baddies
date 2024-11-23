package com.smartprogrammingbaddies;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.smartprogrammingbaddies.auth.ApiKey;
import com.smartprogrammingbaddies.auth.ApiKeyRepository;
import com.smartprogrammingbaddies.auth.AuthController;
import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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

  private static String apiKey = TestUtils.apiKey;
  private String badApiKey = TestUtils.badApiKey;
  private static final String prefix = "Be sure to save this unique API key: ";

  @Test
  public void generateApiKeyTest() throws Exception {
    MvcResult result = mockMvc.perform(
            get("/generateApiKey")).andExpect(status().isOk()).andReturn();
    String response = result.getResponse().getContentAsString();
  }

  @Test
  public void verifyApiKeyTest() throws Exception {
    ResponseEntity<?> mockResponse = new ResponseEntity<>("API key not found in DB.",
            HttpStatus.NOT_FOUND);

    Mockito.when(apiKeyRepository.existsByApiKey(apiKey)).thenReturn(true);
    MvcResult result = mockMvc.perform(get("/verifyApiKey")
                    .param("apiKey", apiKey))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  public void verifyApiKeyTestFail() throws Exception {
    MvcResult result = mockMvc.perform(get("/verifyApiKey")
                    .param("apiKey", badApiKey))
            .andExpect(status().isNotFound())
            .andReturn();
  }

  @Test
  public void removeApiKeyTest() throws Exception {
    ApiKey mockApiKey = new ApiKey(apiKey);
    Mockito.when(apiKeyRepository.findByApiKey(apiKey))
            .thenReturn(Optional.of(mockApiKey));

    mockMvc.perform(delete("/removeApiKey")
                    .param("apiKey", apiKey))
            .andExpect(status().isOk());
  }

  @Test
  public void removeApiKeyTestBad() throws Exception {
    mockMvc.perform(delete("/removeApiKey")
                    .param("apiKey", badApiKey))
            .andExpect(status().isNotFound());
  }
}
