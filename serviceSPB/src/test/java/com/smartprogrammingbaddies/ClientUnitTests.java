package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.smartprogrammingbaddies.client.Client;
import com.smartprogrammingbaddies.organization.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Client class.
 */
public class ClientUnitTests {

  private Client testClient;
  private Organization testOrg;

  /**
   * The Client set up to be tested.
   */
  @BeforeEach
  public void setupClientForTesting() {
    testClient = new Client("test");
    testOrg = new Organization("CUFP", "Food Pantry", testClient);
    testClient.setId(0);
    testClient.setOrganization(testOrg);
  }

  /**
   * Tests the constructor to verify the API key is correct.
   */
  @Test
  public void constructorTest() {
    Client testClient = new Client("test");
    assertEquals("test", testClient.getApiKey());
  }

  /**
   * Tests the getId method to verify the ID is correct.
   */
  @Test
  public void getIdTest() {
    assertEquals(0, testClient.getId());
  }

  /**
   * Tests the setId method to verify the ID is correct.
   */
  @Test
  public void setIdTest() {
    testClient.setId(1);
    assertEquals(1, testClient.getId());
  }

  /**
   * Tests the getOrganization method to verify the organization is correct.
   */
  @Test
  public void getOrganizationTest() {
    assertEquals(testOrg, testClient.getOrganization());
  }

  /**
   * Tests the setOrganization method to verify the organization is correct.
   */
  @Test
  public void setOrganizationTest() {
    Organization newOrg = new Organization("CUFP", "Food Pantry", testClient);
    testClient.setOrganization(newOrg);
    assertEquals(newOrg, testClient.getOrganization());
  }

  /**
   * Tests the getApiKey method to verify the API key is correct.
   */
  @Test
  public void getApiKeyTest() {
    assertEquals("test", testClient.getApiKey());
  }

  /**
   * Tests the setApiKey method to verify the API key is correct.
   */
  @Test
  public void setApiKeyTest() {
    testClient.setApiKey("new");
    assertEquals("new", testClient.getApiKey());
  }

}
