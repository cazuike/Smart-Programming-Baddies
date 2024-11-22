package com.smartprogrammingbaddies;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.sql.DataSource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * This class contains unit tests for Auth Controller.
 *
 * <p> Unit tests involve setting up an environment for testing and conducting the
 * necessary tests to ensure functionality. </p>
 */
@Disabled
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AuthTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DataSource dataSource;
  private static String apiKey;
  private static final String prefix = "Be sure to save this unique API key: ";

  @Test
  public void generateApiKeyTest() throws Exception {
    MvcResult result = mockMvc.perform(
            get("/generateApiKey")).andExpect(status().isOk()).andReturn();
    String response = result.getResponse().getContentAsString();
    apiKey = TestUtils.extract(prefix, response);
  }

  @Test
  public void verifyApiKeyTest() throws Exception {
    if (apiKey == null) {
      generateApiKeyTest();
    }
    MvcResult result = mockMvc.perform(get("/verifyApiKey")
                    .param("apiKey", apiKey))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  public void removeApiKeyTest() throws Exception {
    if (apiKey == null) {
      generateApiKeyTest();
    }
    mockMvc.perform(delete("/removeApiKey")
                    .param("apiKey", apiKey))
            .andExpect(status().isOk());
    apiKey = null;
  }
}
