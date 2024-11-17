package com.smartprogrammingbaddies;

/**
 * Test Utils is a class that provides testing utilities across
 * multiple tests, such as a test apiKey and extraction functions.
 */
public class TestUtils {
  public static String apiKey = "test-service-key";

  public static String extract(String p, String s) {
    int startIndex = s.indexOf(p);
    if (startIndex == -1) {
      throw new IllegalArgumentException("Prefix not found in the input string.");
    }
    return s.substring(startIndex + p.length()).trim();
  }

}
