package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.smartprogrammingbaddies.organization.Organization;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

  /**
   * Sets up the Organization instance and schedule before each test.
   */
  @BeforeEach
  public void setUpOrganizationForTesting() throws ParseException {
    Set<String> schedule = new HashSet<>();
    schedule.add("10-17-2024 10:00 AM");

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
    Date dateAdded = dateFormat.parse("10-17-2024");

    testOrganization = new Organization("UpperBestSide", "For Profit", null);
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
   * Tests the getSchedule() method to verify the organization schedule is correct.
   */
//  @Test
//  public void getScheduleTest() {
//    Set<String> expectedSchedule = new HashSet<>();
//    expectedSchedule.add("10-17-2024 10:00 AM");
//    assertEquals(expectedSchedule, testOrganization.getSchedule());
//  }
//
//  /**
//   * Tests the getDateAdded() method to verify the organization date added is correct.
//   */
//  @Test
//  public void getDateAddedTest() throws ParseException {
//    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
//    Date expectedDateAdded = dateFormat.parse("10-17-2024");
//    assertEquals(expectedDateAdded, testOrganization.getDateAdded());
//  }
}
