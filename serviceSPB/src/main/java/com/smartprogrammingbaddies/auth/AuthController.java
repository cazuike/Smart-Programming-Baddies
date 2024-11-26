package com.smartprogrammingbaddies.auth;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains the AuthController class.
 */
@RestController
@Service
public class AuthController {
  @Autowired
  private ApiKeyRepository apiKeyRepository;

  /**
    * Generates a unique API key for a client.
    *
    * @return A {@code ResponseEntity} containing the generated API key with an
  HTTP 200 status code, or a HTTP 500 status code if API could not be generated.
    */
  @GetMapping("/generateApiKey")
  public ResponseEntity<?> generateApiKey() {
    try {
      String apiKey = UUID.randomUUID().toString();
      while (apiKeyRepository.existsByApiKey(apiKey)) {
        apiKey = UUID.randomUUID().toString();
      }
      apiKeyRepository.save(new ApiKey(apiKey));
      return ResponseEntity.ok(apiKey);
    } catch (Exception e) {
      String message = "Error generating API key: " + e.getMessage();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
  }

  /**
    * Verifies a client's API key is valid.
    *
    * @param apiKey A {@code String} representing the client's API key.
    * @return A {@code ReponseEntity} containing a {@code ResponseEntity}
  with a message if the API key was successfully verified and a HTTP 200 status code,
  or a HTTP 403 status code if the API key was not found.
  */
  @GetMapping("/verifyApiKey")
  public ResponseEntity<?> verifyApiKey(@RequestParam("apiKey") String apiKey) {
    try {
      if (apiKeyRepository.existsByApiKey(apiKey)) {
        return ResponseEntity.ok("API key is valid");
      }

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("API key is invalid");

    } catch (Exception e) {
      String message = "Error verifying API key: " + e.getMessage();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
  }
}