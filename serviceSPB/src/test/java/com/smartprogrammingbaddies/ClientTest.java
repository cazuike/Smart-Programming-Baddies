package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.smartprogrammingbaddies.client.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Unit tests for the Client class.

 */
public class ClientTest {
  /**
   * Sets up the Client instance before each test.
   */
  @BeforeEach
  public void setupClientForTesting() {
    testClient = new Client();

  }

  /**
   * Tests the getClientId() method to verify the client ID is correct.
   */
  @Test
  public void addClientTest() {
    String clientId = testClient.getClientId();
    testClient.addClient(clientId);
    String expectedResponse = "Client successfuly added!";
    String actualResponse = testClient.addClient(clientId);
    assertEquals(expectedResponse, actualResponse);
  }

  /**
   * Tests the addClient() method to verify the client already exists.
   */
  @Test
  public void verifyClientExistsTest(String clientId) {
    String expectedResponse = "Client already exists";
    String actualResponse = testClient.addClient(clientId);
    assertEquals(expectedResponse, actualResponse);
  }
  
  /**
   * The Client set up to be tested.
   */
  public Client testClient;
}
