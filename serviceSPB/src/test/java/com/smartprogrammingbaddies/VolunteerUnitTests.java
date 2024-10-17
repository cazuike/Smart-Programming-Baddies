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

  @Test
  public void getNameTest() {
    String expectedResult = "John Doe";
    assertEquals(expectedResult, testVolunteer.getName());
  }

  @Test
  public void getRoleTest() {
    String expectedResult = "Cook";
    assertEquals(expectedResult, testVolunteer.getRole());
  }

  @Test
  public void getDateSignUpTest() {
    String expectedResult = "10-17-2024";
    assertEquals(expectedResult, testVolunteer.getDateSignUp());
  }

  @Test
  public void updateRoleTest() {
    testVolunteer.updateRole("Server");
    String expectedResult = "Server";
    assertEquals(expectedResult, testVolunteer.getRole());
  }

  @Test
  public void updateScheduleTest() {
    Map<String, String> testVolunteerNewSchedule = new HashMap<>();
    testVolunteerNewSchedule.put("10-30-2025", "9 AM - 12 PM");
    testVolunteer.updateSchedule(testVolunteerNewSchedule);
    assertEquals(testVolunteerNewSchedule, testVolunteer.getSchedule());
  }

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
