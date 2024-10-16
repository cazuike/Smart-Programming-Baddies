package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Organization class.
 
 */
public class OrganizationsUnitTest {
  /**
   * The Organization set up to be tested.
   */
  @BeforeEach
  public void setUpOrganizationForTesting() {
    Map<String, String> schedule = new HashMap<>();
    schedule.put("10-17-2024", "10:00 AM");
    testOrganization = new Organization("UpperBestSide", "For Profit", schedule, "10-17-2024");
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

  @Test
  public void getScheduleTest() {
    Map<String, String> expectedSchedule = new HashMap<>();
    expectedSchedule.put("10-17-2024", "10:00 AM");
    assertEquals(expectedSchedule, testOrganization.getSchedule());
  }
  /**
   * Tests the getDateAdded() method to verify the organization date added is correct.
   */

  @Test
  public void getDateAddedTest() {
    String expectedDateAdded = "10-17-2024";
    assertEquals(expectedDateAdded, testOrganization.getDateAdded());
  }

  public static Organization testOrganization;
}
