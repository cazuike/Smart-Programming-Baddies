package com.smartprogrammingbaddies;


import com.google.api.core.ApiFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
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
  private SetupDatabase setupDatabase;

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
      setupDatabase.enrollClient(apiKey);
      return new ResponseEntity<>("Be sure to save this unique API key: " + apiKey, HttpStatus.OK);
    } catch (Exception e) {
      HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
      return new ResponseEntity<>("Failed to generate API key", statusCode);
    }
  }

  /**
   * Verifies a client's API key is valid.
   *
   * @param apiKey A {@code String} representing the client's API key.
   * @return A {@code CompletableFuture} containing a {@code ResponseEntity}
      with a message if the API key was successfully verified and a HTTP 200 status code,
      or a HTTP 404 status code if the API key was not found.
   */
  @GetMapping("/verifyApiKey")
  public CompletableFuture<ResponseEntity<?>> verifyApiKey(@RequestParam("apiKey") String apiKey) {
    return setupDatabase.verifyClient(apiKey).thenApply(verified -> {
      if (verified) {
        return new ResponseEntity<>("Successfully verified API key", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("API key not found in DB.", HttpStatus.NOT_FOUND);
      }
    });
  }

  /**
   * Removes a client's API key from the database.
   *
   * @param apiKey A {@code String} representing the client's API key.
   * @return A {@code ResponseEntity} containing a message if the API key was
      successfully removed with a HTTP 200 status code, or a HTTP 500 status code
      if the API key could not be removed.
   */
  @DeleteMapping("/removeApiKey")
  public ResponseEntity<?> removeApiKey(@RequestParam("apiKey") String apiKey) {
    try {
      DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clients/" + apiKey);
      ApiFuture<Void> future = ref.removeValueAsync();
      future.get();
      return new ResponseEntity<>("Successfully removed API Key", HttpStatus.OK);
    } catch (Exception e) {
      HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
      return new ResponseEntity<>("Unable to remove API key", statusCode);
    }
  }
}
