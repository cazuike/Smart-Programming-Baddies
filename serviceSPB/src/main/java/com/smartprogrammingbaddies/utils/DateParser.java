package com.smartprogrammingbaddies.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * The DateParser class is used to parse a string into a Date object and vice-versa.
 */
public class DateParser {

  /**
   * Converts a string to a Date object.
   *
   * @param date the string to convert
   * @return the Date object that corresponds to the string
   * @throws ParseException if the string cannot be parsed
   */
  public static Date StringToNumericDate(String date) throws ParseException {
    DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.LONG);
    Date parsedDate = dateFormatter.parse(date);
    return parsedDate;
  }

  /**
   * Converts a Date object to a string.
   *
   * @param date the Date object to convert
   * @return the string that corresponds to the Date object
   */
  public String NumericDateToString(Date date) {
    DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.LONG);
    String formattedDate = dateFormatter.format(date);
    return formattedDate;
  }
}
