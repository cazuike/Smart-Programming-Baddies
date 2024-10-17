package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Volunteer class.
 */
@SpringBootTest
@ContextConfiguration
public class VolunteerUnitTests {

  /**
   *  The Volunteer set up to be tested.
   */
  @BeforeEach
  public void setupVolunteerForTesting() {
    Map<String, String> testVolunteerSchedule = new HashMap<>();
    testVolunteerSchedule.put("10-30-2024", "9 AM - 12 PM");

    testVolunteer = new Volunteer("John Doe", "Cook", "10-17-2024", testVolunteerSchedule);
  }

  /**
   * Tests the .getName() method to verify that it returns the correct volunteer name.
   */
  @Test
  public void getNameTest() {
    String expectedResult = "John Doe";
    assertEquals(expectedResult, testVolunteer.getName());
  }

  /**
   * Tests the .getRole() method to verify that it returns the correct role.
   */
  @Test
  public void getRoleTest() {
    String expectedResult = "Cook";
    assertEquals(expectedResult, testVolunteer.getRole());
  }

  /**
   * Tests the .getDateSignUp method to verify that it returns the correct sign-up date.
   */
  @Test
  public void getDateSignUpTest() {
    String expectedResult = "10-17-2024";
    assertEquals(expectedResult, testVolunteer.getDateSignUp());
  }

  /**
   * Tests the updateRole() method to verify that the role is updated correctly.
   */
  @Test
  public void updateRoleTest() {
    testVolunteer.updateRole("Server");
    String expectedResult = "Server";
    assertEquals(expectedResult, testVolunteer.getRole());
  }
  
  /**
   * Tests the updateSchedule() method to verify that the schedule is updated correctly.
   */
  @Test
  public void updateScheduleTest() {
    Map<String, String> testVolunteerNewSchedule = new HashMap<>();
    testVolunteerNewSchedule.put("10-30-2025", "9 AM - 12 PM");
    testVolunteer.updateSchedule(testVolunteerNewSchedule);
    assertEquals(testVolunteerNewSchedule, testVolunteer.getSchedule());
  }

  /**
   * Tests the toString() method to verify the string representation of the volunteer.
   */
  @Test
  public void toStringTest() {
    String expectedString = "Volunteer: John Doe\n"
                            + "Role: Cook\n"
                            + "Date Signed: 10-17-2024\n"
                            + "Schedule: \n"
                            + "Date: 10-30-2024\nTimes: 9 AM - 12 PM\n";

    assertEquals(expectedString, testVolunteer.toString());
  }

  /** The test volunteer instance used for testing. */
  public static Volunteer testVolunteer;
}
