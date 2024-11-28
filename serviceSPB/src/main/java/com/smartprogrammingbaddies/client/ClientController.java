package com.smartprogrammingbaddies.client;

import com.smartprogrammingbaddies.auth.ApiKey;
import com.smartprogrammingbaddies.auth.ApiKeyRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Client Controller class where you can register a Client and recieve an API key.
 */
@RestController
public class ClientController {
  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private ApiKeyRepository apiKeyRepository;

  /**
   * Generates a unique API key for a client.
   *
   * @return A {@code String} representing the generated API key.
   */
  @PostMapping("/registerClient")
  public ResponseEntity<?> addClient() {
    try {
      String apiKey = generateApiKey();
      Client client = new Client(apiKey);
      clientRepository.save(client);
      ApiKey apiKeyEntity = apiKeyRepository.findByApiKey(apiKey).orElseThrow();
      return ResponseEntity.ok(apiKeyEntity);

    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error adding client: " + e.getMessage());
    }
  }

  /**
   * Generates a unique API key.
   *
   * @return A {@code String} representing the generated API key.
   */
  private String generateApiKey() {
    String apiKey = UUID.randomUUID().toString();
    while (apiKeyRepository.existsByApiKey(apiKey)) {
      apiKey = UUID.randomUUID().toString();
    }
    apiKeyRepository.save(new ApiKey(apiKey));
    return apiKey;
  }
}
