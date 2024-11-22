package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.smartprogrammingbaddies.event.Event;
import com.smartprogrammingbaddies.organization.Organization;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import com.smartprogrammingbaddies.utils.TimeSlot;
import com.smartprogrammingbaddies.volunteer.Volunteer;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

/**
* Unit tests for the Event class.
*/
@Disabled
@ContextConfiguration(classes = {Event.class, StorageCenter.class, Volunteer.class})
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
    testStorageCenter = new StorageCenter();
    testOrganizer = new Organization("Charity Org", "Non-Profit", new HashSet<>(), "12-01-2024");

    Volunteer volunteer1 = new Volunteer("John Doe", "Helper", "12-01-2024", null);
    Volunteer volunteer2 = new Volunteer("Jane Smith", "Cook", "12-01-2024", null);

    testVolunteers = new HashSet<>();
    testVolunteers.add(volunteer1);
    testVolunteers.add(volunteer2);

    TimeSlot eventTime = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(14, 0));

    testEvent = new Event(
        "Charity Drive",
        "A community charity event",
        "2024-12-25", // Date in String format
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
  public void getOrganizerTest() {
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
    Volunteer newVolunteer = new Volunteer("Alice Doe", "Driver", "12-02-2024", null);
    testEvent.addVolunteer(newVolunteer);

    int expectedVolunteerCount = 3;
    assertEquals(expectedVolunteerCount, testEvent.getVolunteerCount());
  }

  // @Test
  // public void removeVolunteerTest() {
  // Volunteer volunteerToRemove = testVolunteers.iterator().next();
  // testEvent.removeVolunteer(volunteerToRemove.getDatabaseId());
  //
  // int expectedVolunteerCount = 1;
  // assertEquals(expectedVolunteerCount, testEvent.getVolunteerCount());
  // }
  //
  // @Test
  // public void toStringWithVolunteersTest() { - issue is with volunteers, they
  // print random order
  // String expectedString = "Event Name: Charity Drive\n"
  // + "Description: A community charity event\n"
  // + "Date: Wed Dec 25 00:00:00 EST 2024\n"
  // + "Time: Wed Dec 25 10:00:00 EST 2024\n"
  // + "Location: East Village\n"
  // + "Storage Center: Food Pantry\n"
  // + "Organizer: Organization Name: Charity Org\n"
  // + "Organizaton Type: Non-Profit\n"
  // + "Date Added: 12-01-2024\n"
  // + "Schedule: \n\n"
  // + "Volunteer Names: \n"
  // + "- John Doe - 0\n"
  // + "- Jane Smith - 0\n";
  //
  // assertEquals(expectedString, testEvent.toString());
  // }
  //
  // @Test
  // public void toStringWithNoVolunteersTest() {
  // testEvent.getListOfVolunteers().clear();
  // String expectedString = "Event Name: Charity Drive\n"
  // + "Description: A community charity event\n"
  // + "Date: Wed Dec 25 00:00:00 EST 2024\n"
  // + "Time: Wed Dec 25 10:00:00 EST 2024\n"
  // + "Location: East Village\n"
  // + "Storage Center: Food Pantry\n"
  // + "Organizer: Organization Name: Charity Org\n"
  // + "Organizaton Type: Non-Profit\n"
  // + "Date Added: 12-01-2024\n"
  // + "Schedule: \n\n"
  // + "No volunteers have signed up yet.\n";
  //
  // assertEquals(expectedString, testEvent.toString());
  // }

}