package com.smartprogrammingbaddies.storagecenter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.smartprogrammingbaddies.item.Item;
import com.smartprogrammingbaddies.storageCenter.StorageCenter;
import com.smartprogrammingbaddies.utils.TimeSlot;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the StorageCenter class.
 */
@SpringBootTest
@ContextConfiguration(classes = {StorageCenter.class, TimeSlot.class, Item.class})
public class StorageCenterUnitTests {

  public static StorageCenter testCenter;
  public static TimeSlot mondayTimeSlot;
  public static TimeSlot fridayTimeSlot;
  public static Map<DayOfWeek, TimeSlot> testHours;
  public static Set<Item> testItems;

  /**
    * The StorageCenter set up to be tested.
   */
  @BeforeEach
  public void setupStorageCenterForTesting() {
    testCenter = new StorageCenter("CUFP", "Food Pantry");
    testHours = new EnumMap<>(DayOfWeek.class);
    LocalTime start = LocalTime.of(10, 0);
    LocalTime end = LocalTime.of(19, 0);
    fridayTimeSlot = new TimeSlot(start, end);
    testHours.put(DayOfWeek.FRIDAY, fridayTimeSlot);
  }

  /** Name Tests */
  @Test
  public void getNameTest() {
    assertEquals("CUFP", testCenter.getName());
  }

  @Test
  public void updateNameTest() {
    testCenter.changeName("Food Drive");
    assertEquals("Food Drive", testCenter.getName());
  }

  @Test
  public void updateNameFailTest() {
    boolean changed = testCenter.changeName("");
    assertFalse(changed);
    assertEquals("CUFP", testCenter.getName());

    changed = testCenter.changeName(null);
    assertFalse(changed);
    assertEquals("CUFP", testCenter.getName());

    changed = testCenter.changeName(" ");
    assertFalse(changed);
    assertEquals("CUFP", testCenter.getName());
  }

  /** Description Tests */
  @Test
  public void getDescriptionTest() {
    assertEquals("Food Pantry", testCenter.getDescription());
  }

  @Test
  public void updateDescriptionTest() {
    testCenter.changeDescription("Clothes Pantry");
    assertEquals("Clothes Pantry", testCenter.getDescription());
  }

  @Test
  public void updateDescriptionFailTest() {
    boolean changed = testCenter.changeDescription("");
    assertFalse(changed);
    assertEquals("Food Pantry", testCenter.getDescription());

    changed = testCenter.changeDescription(null);
    assertFalse(changed);
    assertEquals("Food Pantry", testCenter.getDescription());

    changed = testCenter.changeDescription(" ");
    assertFalse(changed);
    assertEquals("Food Pantry", testCenter.getDescription());
  }

  /** Hours Tests */
  @Test
  public void getHoursTest() {
    Map<DayOfWeek, TimeSlot> expectedHours = new EnumMap<>(DayOfWeek.class);
    Map<DayOfWeek, TimeSlot> testHours = testCenter.getOperatingHours();
    assertEquals(expectedHours, testHours);
  }

  @Test
  public void updateHoursTest() {
    LocalTime start = LocalTime.of(10, 0);
    LocalTime end = LocalTime.of(19, 0);
    fridayTimeSlot = new TimeSlot(start, end);
    testCenter.updateDayHours(start, end, DayOfWeek.FRIDAY);
    Map<DayOfWeek, TimeSlot> updatedHours = testCenter.getOperatingHours();
    assertEquals(fridayTimeSlot, updatedHours.get(DayOfWeek.FRIDAY));
  }

  /** Items Tests */
  @Test
  public void getItemsTest() {
    Set<Item> expectedItems = testCenter.getItems();
    assertEquals(expectedItems, testCenter.getItems());
  }

  @Test
  public void setItemsTest() {
    Set<Item> expectedItems = testCenter.getItems();
    testCenter.setItems(expectedItems);
    assertEquals(expectedItems, testCenter.getItems());
  }

  @Test
  public void setItemsFailTest() {
    Set<Item> expectedItems = testCenter.getItems();
    testCenter.setItems(null);
    assertNotEquals(expectedItems, testCenter.getItems());
  }

  /** Print Statement tests. */
  @Test
  public void printItemsTest() {
    StringBuilder result = new StringBuilder();
    result.append("Items: ").append("\n");
    String expected = result.toString();
    assertEquals(expected, testCenter.printItems());
  }

  @Test
  public void toStringTest() {
    Map<DayOfWeek, TimeSlot> operationHours = new EnumMap<>(DayOfWeek.class);
    StringBuilder result = new StringBuilder();
    result.append("Storage Center Name: ").append(testCenter.getName()).append("\n");
    result.append("Description: ").append(testCenter.getDescription()).append("\n");
    result.append("Operating Hours: ").append("\n");
    operationHours.forEach((day, hours) -> {
      result.append(day).append(": ").append(hours).append("\n");
    });
    result.append("Items: ").append("\n");
    String expected = result.toString();
    assertEquals(expected, testCenter.toString());
  }
}
