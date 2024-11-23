package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.smartprogrammingbaddies.volunteer.Volunteer;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Volunteer class.
 */
@ContextConfiguration
public class VolunteerUnitTests {

  /** The test volunteer instance used for testing. */
  public static Volunteer testVolunteer;

  /**
    * Sets up the Volunteer instance and schedule before each test.
    */
  @BeforeEach
  public void setupVolunteerForTesting() {
    Set<String> testVolunteerSchedule = new HashSet<>();
    testVolunteerSchedule.add("10-30-2024");

    testVolunteer = new Volunteer("John Doe", "Cook", null);
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
//  @Test
//  public void getDateSignUpTest() {
//    String expectedResult = "10-17-2024";
//    assertEquals(expectedResult, testVolunteer.getDateSignUp());
//  }

  /**
    * Tests the updateRole() method to verify that the role is updated correctly.
    */
  @Test
  public void updateRoleTest() {
    testVolunteer.setRole("Server");
    String expectedResult = "Server";
    assertEquals(expectedResult, testVolunteer.getRole());
  }

  //    @Test
  //    public void updateScheduleTest() {
  //        Set<String> testVolunteerNewSchedule = new HashSet<>();
  //        testVolunteerNewSchedule.add("10-30-2025");
  //
  //        testVolunteer.updateSchedule(testVolunteerNewSchedule);
  //
  //        assertEquals(testVolunteerNewSchedule, testVolunteer.getSchedule());
  //    }

  //    /**
  //     * Tests the toString() method to verify the string representation of the volunteer.
  //     */
  //    @Test
  //    public void toStringTest() {
  //        String expectedString = "Volunteer: John Doe\n"
  //                + "Role: Cook\n"
  //                + "Date Signed: 10-17-2024\n"
  //                + "Schedule: \n"
  //                + "Date: 10-30-2024\nTimes: 10-30-2024\n";
  //
  //        assertEquals(expectedString, testVolunteer.toString());
  //    }
}
