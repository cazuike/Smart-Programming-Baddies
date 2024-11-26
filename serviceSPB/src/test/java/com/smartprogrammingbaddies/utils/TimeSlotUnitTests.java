package com.smartprogrammingbaddies.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the TimeSlot class.
 */
public class TimeSlotUnitTests {

  private TimeSlot testTimeSlot;
  private TimeSlot testStringTimeSlot;

  /**
   * The TimeSlot set up to be tested.
   */
  @BeforeEach
  public void setupTimeSlotForTesting() {
    testTimeSlot = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(19, 0));
    testStringTimeSlot = new TimeSlot("10:00", "19:00");
  }

  /**
   * Tests the constructor to verify the start and end times are correct.
   */
  @Test
  public void constructorTest() {
    LocalTime expectedStartTime = LocalTime.of(10, 0);
    LocalTime expectedEndTime = LocalTime.of(19, 0);

    assertEquals(expectedStartTime, testTimeSlot.getStartTime());
    assertEquals(expectedEndTime, testTimeSlot.getEndTime());

    assertEquals(expectedStartTime, testStringTimeSlot.getStartTime());
    assertEquals(expectedEndTime, testStringTimeSlot.getEndTime());
  }

  /**
   * Tests the constructor to verify it throws DateTimeParseException on invalid input.
   */
  @Test
  public void constructorInvalidFormatTest() {
    assertThrows(DateTimeParseException.class, () -> {
      new TimeSlot("a0:00", "19:00");
    });
  }

  /**
   * Tests the contructor to verify that the start time is before the end time.
   */
  @Test
  public void constructorInvalidTimeTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      new TimeSlot(LocalTime.of(19, 0), LocalTime.of(10, 0));
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new TimeSlot("19:00", "10:00");
    });
  }

  /**
   * Tests the getStartTime() method to verify the start time is correct.
   */
  @Test
  public void getStartTimeTest() {
    LocalTime expectedStartTime = LocalTime.of(10, 0);
    assertEquals(expectedStartTime, testTimeSlot.getStartTime());
  }

  /**
   * Tests the getEndTime() method to verify the end time is correct.
   */
  @Test
  public void getEndTimeTest() {
    LocalTime expectedEndTime = LocalTime.of(19, 0);
    assertEquals(expectedEndTime, testTimeSlot.getEndTime());
  }

  /**
   * Test the equals method to verify two TimeSlot objects are equal.
   */
  @Test
  public void equalsTest() {
    TimeSlot testTimeSlot2 = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(19, 0));
    assertTrue(testTimeSlot.equals(testTimeSlot2));
  }

  /**
   * Test the equals method to verify two TimeSlot objects are not equal.
   */
  @Test
  public void notEqualsTest() {
    TimeSlot testTimeSlot2 = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0));
    assertTrue(!testTimeSlot.equals(testTimeSlot2));

    assertTrue(!testTimeSlot.equals(null));

    assertTrue(!testTimeSlot.equals("testObject"));
  }

  /**
   * Test the toString method to verify the string representation is correct.
   */
  @Test
  public void toStringTest() {
    String expectedString = "10:00 - 19:00";
    assertEquals(expectedString, testTimeSlot.toString());
  }

  /**
   * Test the toJson method to verify the JSON object is correct.
   */
  @Test
  public void toJsonTest() {
    String expectedJson = "{\"start\":\"10:00\",\"end\":\"19:00\"}";
    assertEquals(expectedJson, testTimeSlot.toJson().toString());
  }

  /**
   * Test the hashCode method to verify the hash code is correct.
   */
  @Test
  public void hashCodeTest() {
    TimeSlot testTimeSlot2 = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(19, 0));
    assertEquals(testTimeSlot.hashCode(), testTimeSlot2.hashCode());
  }
}
