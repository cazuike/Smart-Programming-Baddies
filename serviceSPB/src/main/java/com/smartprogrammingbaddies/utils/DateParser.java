package com.smartprogrammingbaddies.utils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The DateParser abstract class is used to parse a string into a Date object and vice-versa.
 */
public abstract class DateParser {
  /**
   * Converts a string to a Date object. Must be in the format yyyy-MM-dd.
   *
   * @param date the string to convert
   * @return the Date object that corresponds to the string
   * @throws DateTimeParseException if the string cannot be parsed
   */
  public static LocalDate stringToNumericDate(String date) throws DateTimeParseException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, formatter);
  }

  /**
   * Converts a Date object to a string in the format, yyyy-MM-dd.
   *
   * @param date the Date object to convert
   * @return the string that corresponds to the Date object
   * @throws DateTimeException if the string cannot be parsed
   */
  public static String numericDateToString(LocalDate date) throws DateTimeException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return formatter.format(date);
  }
}
