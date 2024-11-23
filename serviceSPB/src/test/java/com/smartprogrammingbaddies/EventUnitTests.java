package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.smartprogrammingbaddies.event.Event;
import com.smartprogrammingbaddies.organization.Organization;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import com.smartprogrammingbaddies.utils.TimeSlot;
import com.smartprogrammingbaddies.volunteer.Volunteer;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Event class.
 */
@Disabled
public class EventUnitTests {

  public static Event testEvent;
  public static StorageCenter testStorageCenter;
  public static Organization testOrganizer;
  public static Set<Volunteer> testVolunteers;

  /**
   * Sets up the Event instance and related objects before each test.
   */
  @BeforeEach
  public void setupEventForTesting() {
    testStorageCenter = null;
    testOrganizer = new Organization("Charity Org", "Non-Profit", null);

    Volunteer volunteer1 = new Volunteer("John Doe", "Helper", null);
    Volunteer volunteer2 = new Volunteer("Jane Smith", "Cook", null);

    testVolunteers = new HashSet<>();
    testVolunteers.add(volunteer1);
    testVolunteers.add(volunteer2);

    TimeSlot eventTime = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(14, 0));

    testEvent = null;
  }

  @Test
  public void getNameTest() {
    String expectedName = "Charity Drive";
    assertEquals(expectedName, testEvent.getName());
  }

  @Test
  public void updateNameTest() {
    testEvent.setName("Food Drive");
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
    testEvent.setDescription("A community food event");
    String expectedDescription = "A community food event";
    assertEquals(expectedDescription, testEvent.getDescription());
  }

  @Test
  public void getDateTest() {
    assertEquals("2024-12-25", testEvent.getDate());
  }

  @Test
  public void updateDateTest() {
    testEvent.setDate(null);
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
    testEvent.setTime(null);
    assertEquals(newTime, testEvent.getTime());
  }

  @Test
  public void getLocationTest() {
    String expectedLocation = "East Village";
    assertEquals(expectedLocation, testEvent.getLocation());
  }

  @Test
  public void updateLocationTest() {
    testEvent.setLocation("West Village");
    String expectedLocation = "West Village";
    assertEquals(expectedLocation, testEvent.getLocation());
  }

  @Test
  public void getOrganizerTest() {
    assertEquals(testOrganizer, testEvent.getOrganizer());
  }

//  @Test
//  public void getListOfVolunteersTest() {
//    assertEquals(testVolunteers, testEvent.getListOfVolunteers());
//  }
//
//  @Test
//  public void getVolunteerCountTest() {
//    int expectedVolunteerCount = 2;
//    assertEquals(expectedVolunteerCount, testEvent.getVolunteerCount());
//  }

//  @Test
//  public void addVolunteerTest() {
//    Volunteer newVolunteer = new Volunteer("Alice Doe", "Driver", "2024-12-02", null);
//    testEvent.addVolunteer(newVolunteer);
//
//    int expectedVolunteerCount = 3;
//    assertEquals(expectedVolunteerCount, testEvent.getVolunteerCount());
//  }

//  @Test
//  public void removeVolunteerTest() {
//    Volunteer volunteerToRemove = testVolunteers.iterator().next();
//    testEvent.getListOfVolunteers().remove(volunteerToRemove);
//
//    int expectedVolunteerCount = 1;
//    assertEquals(expectedVolunteerCount, testEvent.getVolunteerCount());
//  }

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

//  @Test
//  public void toStringWithNoVolunteersTest() {
//    testEvent.getListOfVolunteers().clear();
//
//    String expectedString = "Event Name: Charity Drive\n"
//            + "Description: A community charity event\n"
//            + "Date: 2024-12-25\n"
//            + "Time: 10:00 - 14:00\n"
//            + "Location: East Village\n"
//            + "Storage Center: null\n"
//            + "Organizer: Organization Name: Charity Org\n"
//            + "No volunteers have signed up yet.";
//
//    assertEquals(expectedString.trim(), testEvent.toString().trim());
//  }

}
