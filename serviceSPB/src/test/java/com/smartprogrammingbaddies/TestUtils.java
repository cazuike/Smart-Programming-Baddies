package com.smartprogrammingbaddies;

public class TestUtils {
    public static String apiKey = "test-service-key";

    public static String extract(String p, String s) {
        return s.substring(s.indexOf(p) + p.length()).trim();
    }
}
