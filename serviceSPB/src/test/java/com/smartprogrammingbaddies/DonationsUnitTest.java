package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Unit tests for the Donations class.

 */
@ActiveProfiles("test")
@SpringBootTest
public class DonationsUnitTest {

  /**
  * The Donations set up to be tested.
  */
  @BeforeEach
  public void setupDonationsForTesting() {
    testDonations = new Donations("Clothes", "Clothing", "John Doe", "1 year", "East Village");
  }

  /**
  * Tests the getDonationName() method to verify the donation name is correct.
  */
  @Test
  public void getDonationNameTest() {
    String expectedName = "Clothes";
    assertEquals(expectedName, testDonations.getDonationName());
  }

  /**
  * Tests the getDonationType() method to verify the donation type is correct.
  */
  @Test
  public void getDonationTypeTest() {
    String expectedType = "Clothing";
    assertEquals(expectedType, testDonations.getDonationType());
  }

  /**
  * Tests the getDonator() method to verify the donator name is correct.
  */
  @Test
  public void getDonatorTest() {
    String expectedDonator = "John Doe";
    assertEquals(expectedDonator, testDonations.getDonator());
  }

  /**
  * Tests the getLifeSpan() method to verify the donation lifespan is correct.
  */
  @Test
  public void getLifeSpanTest() {
    String expectedLifeSpan = "1 year";
    assertEquals(expectedLifeSpan, testDonations.getLifeSpan());
  }

  public Donations testDonations;

}
