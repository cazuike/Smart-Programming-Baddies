package com.smartprogrammingbaddies.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;

/**
* Unit tests for the DateParser class.
*/
public class DateParserUnitTests {
  /**
   * Tests the stringToNumericDate method.
   *
   * @throws ParseException if the string cannot be parsed
   */
  @Test
  public void stringToNumericDateTest() throws ParseException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse("2024-01-01", formatter);
    LocalDate numericDate = DateParser.stringToNumericDate("2024-01-01");
    assertEquals(date, numericDate);

    String actual = DateParser.numericDateToString(date);
    assertEquals("2024-01-01", actual);
  }

  /**
  * Tests the numericDateToString method.
  */
  @Test
  public void numericDateToStringTest() throws ParseException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate numericDate = LocalDate.parse("2024-01-01", formatter);
    String actual = DateParser.numericDateToString(numericDate);
    assertEquals("2024-01-01", actual);
  }

  /**
   * Tests the stringToNumericDate method with an invalid date.
   */
  @Test
  public void stringToNumericDateInvalidTest() {
    assertThrows(DateTimeParseException.class, () -> {
      DateParser.stringToNumericDate("01/01/2021 12:00:00");
    });
  }

  /**
   * Tests the stringToNumericDate method with a null date.
   */
  @Test
  public void stringToNumericDateNullTest() {
    assertThrows(NullPointerException.class, () -> {
      DateParser.stringToNumericDate(null);
    });
  }

  /**
   * Tests the numericDateToString method with a null date.
   */
  @Test
  public void numericDateToStringNullTest() {
    assertThrows(NullPointerException.class, () -> {
      DateParser.numericDateToString(null);
    });
  }

  /**
   * Tests the toJson method.
   */
  @Test
  public void toJsonTest() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse("2024-01-01", formatter);
    assertEquals("{\"date\":\"2024-01-01\"}", DateParser.toJson(date).toString());
  }
}
