package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import com.smartprogrammingbaddies.organization.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Organization class.

 */
@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes = {Organization.class})
public class OrganizationsUnitTest {
    /**
     * The Organization set up to be tested.
     */
    public static Organization testOrganization;

    /**
     * Sets up the Organization instance and schedule before each test.
     */
    @BeforeEach
    public void setUpOrganizationForTesting() {
        Set<String> schedule = new HashSet<>();
        schedule.add("10-17-2024 10:00 AM");

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
        Set<String> expectedSchedule = new HashSet<>();
        expectedSchedule.add("10-17-2024 10:00 AM");
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
}
