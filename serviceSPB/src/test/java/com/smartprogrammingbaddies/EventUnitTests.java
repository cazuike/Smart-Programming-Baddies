package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.smartprogrammingbaddies.client.Client;
import com.smartprogrammingbaddies.event.Event;
import com.smartprogrammingbaddies.organization.Organization;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import com.smartprogrammingbaddies.utils.TimeSlot;
import com.smartprogrammingbaddies.volunteer.Volunteer;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Event class.
 */
public class EventUnitTests {

  public static Event testEvent;
  public static StorageCenter testStorageCenter;
  public static Client testClient;
  public static Organization testOrganizer;
  public static Set<Volunteer> testVolunteers;

  /**
   * Sets up the Event instance and related objects before each test.
   */
  @BeforeEach
  public void setupEventForTesting() {
    testStorageCenter = new StorageCenter();
    testClient = new Client("test");
    testOrganizer = new Organization("Charity Org", "Non-Profit", testClient);

    Volunteer volunteer1 = new Volunteer("John Doe", "Helper", "2024-12-01", null);
    Volunteer volunteer2 = new Volunteer("Jane Smith", "Cook", "2024-12-01", null);

    testVolunteers = new HashSet<>();
    testVolunteers.add(volunteer1);
    testVolunteers.add(volunteer2);

    TimeSlot eventTime = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(14, 0));

    testEvent = new Event(
        "Charity Drive",
        "A community charity event",
        "2024-12-25",
        eventTime,
        "East Village",
        testStorageCenter,
        testOrganizer,
        testVolunteers);
  }

  @Test
  public void getNameTest() {
    String expectedName = "Charity Drive";
    assertEquals(expectedName, testEvent.getName());
  }

  @Test
  public void updateNameTest() {
    testEvent.updateName("Food Drive");
    String expectedName = "Food Drive";
    assertEquals(expectedName, testEvent.getName());
  }

  @Test
  public void getDescriptionTest() {
    String expectedDescription = "A community charity event";
    assertEquals(expectedDescription, testEvent.getDescription());
  }

  @Test
  public void updateDescriptionTest() {
    testEvent.updateDescription("A community food event");
    String expectedDescription = "A community food event";
    assertEquals(expectedDescription, testEvent.getDescription());
  }

  @Test
  public void getDateTest() {
    assertEquals("2024-12-25", testEvent.getDate());
  }

  @Test
  public void updateDateTest() {
    testEvent.updateDate("2024-12-26");
    assertEquals("2024-12-26", testEvent.getDate());
  }

  @Test
  public void getTimeTest() {
    TimeSlot expectedTime = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(14, 0));
    assertEquals(expectedTime, testEvent.getTime());
  }

  @Test
  public void updateTimeTest() {
    TimeSlot newTime = new TimeSlot(LocalTime.of(11, 0), LocalTime.of(15, 0));
    testEvent.updateTime(newTime);
    assertEquals(newTime, testEvent.getTime());
  }

  @Test
  public void getLocationTest() {
    String expectedLocation = "East Village";
    assertEquals(expectedLocation, testEvent.getLocation());
  }

  @Test
  public void updateLocationTest() {
    testEvent.updateLocation("West Village");
    String expectedLocation = "West Village";
    assertEquals(expectedLocation, testEvent.getLocation());
  }

  @Test
  public void getStorageCenterTest() {
    assertEquals(testStorageCenter, testEvent.getStorageCenter());
  }

  @Test
  public void getOrganizerTest() {
    Organization expectedOrganizer =
        new Organization("Charity Org", "Non-Profit", testClient);

    testEvent.updateOrganizer(expectedOrganizer);
    assertEquals(expectedOrganizer, testEvent.getOrganizer());
  }

  @Test
  public void updateOrganizerTest() {
    assertEquals(testOrganizer, testEvent.getOrganizer());
  }

  @Test
  public void getListOfVolunteersTest() {
    assertEquals(testVolunteers, testEvent.getListOfVolunteers());
  }

  @Test
  public void getVolunteerCountTest() {
    int expectedVolunteerCount = 2;
    assertEquals(expectedVolunteerCount, testEvent.getVolunteerCount());
  }

  @Test
  public void addVolunteerTest() {
    Volunteer newVolunteer = new Volunteer("Alice Doe", "Driver", "2024-12-02", null);
    testEvent.addVolunteer(newVolunteer);

    int expectedVolunteerCount = 3;
    assertEquals(expectedVolunteerCount, testEvent.getVolunteerCount());
  }

  @Test
  public void removeVolunteerTest() {
    Volunteer volunteerToRemove = testVolunteers.iterator().next();
    testEvent.getListOfVolunteers().remove(volunteerToRemove);

    int expectedVolunteerCount = 1;
    assertEquals(expectedVolunteerCount, testEvent.getVolunteerCount());
  }

  @Test
  public void isCancelledFalseTest() {
    boolean expectedResult = false;
    assertEquals(expectedResult, testEvent.isCancelled());
  }

  @Test
  public void isCancelledTrueTest() {
    boolean expectedResult = true;
    testEvent.cancelEvent();
    assertEquals(expectedResult, testEvent.isCancelled());
  }

  @Test
  public void toStringWithVolunteersTest() {
    String expectedString = "Event Name: Charity Drive\n"
        + "Description: A community charity event\n"
        + "Date: 2024-12-25\n"
        + "Time: 10:00 - 14:00\n"
        + "Location: East Village\n"
        + "Storage Center: null\n"
        + "Organizer: Organization Name: Charity Org\n"
        + "Volunteer Names: \n"
        + "- Jane Smith - 0\n"
        + "- John Doe - 0\n";

    assertEquals(expectedString.trim(), testEvent.toString().trim());
  }

  @Test
  public void toStringWithNoVolunteersTest() {
    testEvent.getListOfVolunteers().clear();

    String expectedString = "Event Name: Charity Drive\n"
        + "Description: A community charity event\n"
        + "Date: 2024-12-25\n"
        + "Time: 10:00 - 14:00\n"
        + "Location: East Village\n"
        + "Storage Center: null\n"
        + "Organizer: Organization Name: Charity Org\n"
        + "No volunteers have signed up yet.";

    assertEquals(expectedString.trim(), testEvent.toString().trim());
  }

}
