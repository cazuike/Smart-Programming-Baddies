package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.smartprogrammingbaddies.client.Client;
import com.smartprogrammingbaddies.event.Event;
import com.smartprogrammingbaddies.organization.Organization;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Organization class.
 */
public class OrganizationUnitTests {
  /**
   * The Organization set up to be tested.
   */
  private static Organization testOrganization;
  private static Client testClient;
  private static StorageCenter testStorageCenter;
  private static Event testEvent;
  private static Set<Event> testEvents;

  /**
   * Sets up the Organization instance and schedule before each test.
   */
  @BeforeEach
  public void setUpOrganizationForTesting() throws ParseException {
    testClient = new Client("test");

    testOrganization = new Organization("UpperBestSide", "For Profit", testClient);

    testStorageCenter = new StorageCenter("UpperBestSide", "For Profit");

    testEvent = new Event(
        "Charity Drive",
        "A community charity event",
        "2024-12-25",
        null,
        "East Village",
        testStorageCenter,
        testOrganization,
        new HashSet<>());

    testEvents = new HashSet<>();
    testEvents.add(testEvent);
  }

  /**
   * Tests the constructor to verify the name and type are correct.
   */
  @Test
  public void constructorTest() {
    Organization testOrganization = new Organization("UpperBestSide", "For Profit", testClient);
    assertEquals("UpperBestSide", testOrganization.getOrgName());
    assertEquals("For Profit", testOrganization.getOrgType());
  }

  /**
   * Tests the constructor throws an exception when the name, type, or Client are null ir empty.
   */
  @Test
  public void constructorNullNameTest() {
    assertThrows(IllegalArgumentException.class, () ->
        new Organization(null, "For Profit", testClient));

    assertThrows(IllegalArgumentException.class, () ->
        new Organization("", "For Profit", testClient));

    assertThrows(IllegalArgumentException.class, () ->
        new Organization("UpperBestSide", null, testClient));

    assertThrows(IllegalArgumentException.class, () ->
        new Organization("UpperBestSide", "", testClient));

    assertThrows(IllegalArgumentException.class, () ->
        new Organization("UpperBestSide", "For Profit", null));
  }

  /**
   * Tests the getOrgName() method to verify the organization name is correct.
   */
  @Test
  public void getOrgNameTest() {
    String expectedName = "UpperBestSide";
    assertEquals(expectedName, testOrganization.getOrgName());
  }

  /**
   * Tests the getOrgType() method to verify the organization type is correct.
   */
  @Test
  public void getOrgTypeTest() {
    String expectedType = "For Profit";
    assertEquals(expectedType, testOrganization.getOrgType());
  }

  /**
   * Tests the getClient method to verify the client is correct.
   */
  @Test
  public void getClientTest() {
    assertEquals(testClient, testOrganization.getClient());
  }

  /**
   * Tests the setStorage method to verify the storage center is correct.
   */
  @Test
  public void setStorageTest() {
    StorageCenter newStorageCenter = new StorageCenter("UpperBestSide", "For Profit");
    testOrganization.setStorage(newStorageCenter);
    StorageCenter result = testOrganization.getStorage();
    assertEquals(newStorageCenter, result);
  }

  /**
   * Tests the setStorage method to verify the storage center is correct.
   */
  @Test
  public void setStorageNullTest() {
    assertThrows(IllegalArgumentException.class, () -> testOrganization.setStorage(null));
  }

  /**
   * Tests the getEvents method to verify the events are correct.
   */
  @Test
  public void getEventsTest() {
    Set<Event> expectedEvents = new HashSet<>();
    expectedEvents.add(testEvent);
    testOrganization.changeSubscriptionStatus();
    assertNotNull(testOrganization.getEvents());
  }

  /**
   * Tests the addEvent method to verify the event is added correctly.
   */
  @Test
  public void addEventTest() {
    Event newEvent = new Event(
        "Charity Drive",
        "A community charity event",
        "2024-12-25",
        null,
        "East Village",
        testStorageCenter,
        testOrganization,
        new HashSet<>());
    testOrganization.addEvent(newEvent);
    testOrganization.changeSubscriptionStatus();
    assertNotNull(testOrganization.getEvents());
  }

  /**
   * Tests the addEvent method to verify it throws an exception when the event is null.
   */
  @Test
  public void addEventNullTest() {
    assertThrows(IllegalArgumentException.class, () -> testOrganization.addEvent(null));
  }

  /**
   * Tests the removeEvent method to verify the event is removed correctly.
   */
  @Test
  public void removeEventTest() {
    testOrganization.removeEvent(testEvent);
    Set<Event> expectedEvents = new HashSet<>();
    testOrganization.changeSubscriptionStatus();
    assertEquals(expectedEvents, testOrganization.getEvents());
  }

  /**
   * Tests the removeEvent method to verify it throws an exception when the event is null.
   */
  @Test
  public void removeEventNullTest() {
    assertThrows(IllegalArgumentException.class, () -> testOrganization.removeEvent(null));
  }

  /**
   * Tests the notificationSubscribed column to verify it is initialized at false.
   */
  @Test
  public void notificationSubscribedTest() {
    assertEquals(false, testOrganization.getSubscriptionStatus());
  }

  /**
   * Tests the changeSubscriptionStatus method to verify the subscription status is correct.
   */
  @Test
  public void changeSubscriptionStatusTest() {
    testOrganization.changeSubscriptionStatus();
    assertEquals(true, testOrganization.getSubscriptionStatus());
  }
}