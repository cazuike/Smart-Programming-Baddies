package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.smartprogrammingbaddies.event.Event;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import com.smartprogrammingbaddies.volunteer.Volunteer;

/**
 * Unit tests for the Event class.
 */
@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes = {Event.class, StorageCenter.class, Volunteer.class})
public class EventUnitTests {

  /**
   *  The Volunteer set up to be tested.
   */
  @BeforeEach
  public void setupEventForTesting() {
    // testOrganizer = new StorageCenter("Food Pantry");
  }

  /**
   * Tests the getName() method to verify the event name is correct.
   */
  @Test
  public void getNameTest() {
    String expectedName = "Charity Drive";
    assertEquals(expectedName, testEvent.getName());
  }

  /**
   * Tests the updateName() method to verify the event name is correct
   *  after being updated.
   */
  @Test
  public void updateNameTest() {
    testEvent.updateName("Food Drive");
    String expectedName = "Food Drive";
    assertEquals(expectedName, testEvent.getName());
  }

  /**
   * Tests the getDescription() method to verify the event description is correct.
   */
  @Test
  public void getDescriptionTest() {
    String expectedDescription = "A community charity event";
    assertEquals(expectedDescription, testEvent.getDescription());
  }

  /**
   * Tests the updateDescription() method to verify the event description is correct
   * after being updated.
   */
  @Test
  public void updateDescriptionTest() {
    testEvent.updateDescription("A community food event");
    String expectedDescription = "A community food event";
    assertEquals(expectedDescription, testEvent.getDescription());
  }

  /**
   * Tests the getDate() method to verify the event date is correct.
   */
  @Test
  public void getDateTest() {
    String expectedDate = "12-25-2024";
    assertEquals(expectedDate, testEvent.getDate());
  }

  /**
   * Tests the updateDate() method to verify the event date is correct
   * after being updated.
   */
  @Test
  public void updateDateTest() {
    String expectedDate = "12-26-2024";
    assertEquals(expectedDate, testEvent.getDate());
  }

  /**
   * Tests the getTime() method to verify the event time is correct.
   */
  @Test
  public void getTimeTest() {
    String expectedTime = "10:00 AM";
    assertEquals(expectedTime, testEvent.getTime());
  }

  /**
   * Tests the updateTime() method to verify the event time is correct
   * after being updated.
   */
  @Test
  public void updatedTimeTest() {
    String expectedTime = "11:00 AM";
    assertEquals(expectedTime, testEvent.getTime());
  }

  /**
   * Tests the getLocation() method to verify the event location is correct.
   */
  @Test
  public void getLocationTest() {
    String expectedLocation = "East Village";
    assertEquals(expectedLocation, testEvent.getLocation());
  }

  /**
   * Tests the updateLocation() method to verify the event location is correct
   * after being updated.
   */
  @Test
  public void updateLocationTest() {
    testEvent.updateLocation("West Village");
    String expectedLocation = "West Village";
    assertEquals(expectedLocation, testEvent.getLocation());
  }

  /**
   * Tests the getOrganizer() method to verify the event organizer is correct.
   */
  @Test
  public void getOrganizerTest() {
    assertEquals(testOrganizer, testEvent.getOrganizer());
  }

  /**
   * Tests the getListOfVolunteers() method to verify the list of volunteers is correct.
   */
  @Test
  public void getListOfVolunteersTest() {
    assertEquals(testVolunteerMap, testEvent.getListOfVolunteers());
  }

  /**
   * Tests the getVolunteerCount() method to verify the number of volunteers is correct.
   */
  @Test
  public void getVolunteerCountTest() {
    int expectedVolunteerCount = 2;
    assertEquals(expectedVolunteerCount, testEvent.getVolunteerCount());
  }

  /**
   * Tests the addVolunteer() method to verify a new volunteer can be added to the event.
   */
  @Test
  public void addVolunteerTest() {

    int expectedVolunteerCount = 3;
    assertEquals(expectedVolunteerCount, testEvent.getVolunteerCount());
  }

  /**
   * Tests the removeVolunteer() method to verify a volunteer can be removed from the event.
   */
  @Test
  public void removeVolunteerTest() {
    int expectedVolunteerCount = 1;
    assertEquals(expectedVolunteerCount, testEvent.getVolunteerCount());
  }

  /**
   * Tests the toString() method to verify the string representation of the event is correct
   *  when the event has volunteers.
   */
  @Test
  public void toStringWithVoluneersTest() {
    String expectedString = "Event Name: Charity Drive\n"
                            + "Description: A community charity event\n"
                            + "Date: 12-25-2024\n"
                            + "Time: 10:00 AM\n"
                            + "Location: East Village\n"
                            + "Organizer: Food Pantry\n"
                            + "Volunteer Names: \n"
                            + "- John Doe\n"
                            + "- Jane Smith\n";

    assertEquals(expectedString, testEvent.toString());
  }

  /**
   * Tests the toString() method to verify the string representation of the event is correct
   *  when the event has no volunteers.
   */
  @Test
  public void toStringWithNoVoluneersTest() {
    String expectedString = "Event Name: Charity Drive\n"
                            + "Description: A community charity event\n"
                            + "Date: 12-25-2024\n"
                            + "Time: 10:00 AM\n"
                            + "Location: East Village\n"
                            + "Organizer: Food Pantry\n"
                            + "No volunteers signed up yet.\n";
    assertEquals(expectedString, testEvent.toString());
  }

  public static Event testEvent;
  public static StorageCenter testOrganizer;
  public static Map<String, Volunteer> testVolunteerMap;
}
