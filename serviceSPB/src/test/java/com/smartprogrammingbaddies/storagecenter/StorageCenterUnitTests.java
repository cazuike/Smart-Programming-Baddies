package com.smartprogrammingbaddies.storagecenter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.smartprogrammingbaddies.item.Item;
import com.smartprogrammingbaddies.item.ItemId;
import com.smartprogrammingbaddies.item.ItemId.ItemType;
import com.smartprogrammingbaddies.utils.TimeSlot;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the StorageCenter class.
 */
public class StorageCenterUnitTests {

  private StorageCenter testCenter;
  private TimeSlot fridayTimeSlot;
  private Map<DayOfWeek, TimeSlot> testHours;
  private Set<Item> testItems;
  private Item testItem;

  /**
   * The StorageCenter set up to be tested.
   *
   * @throws ParseException if the date could not be parsed
   */
  @BeforeEach
  public void setupStorageCenterForTesting() throws ParseException {
    testCenter = new StorageCenter("CUFP", "Food Pantry");
    testHours = new EnumMap<>(DayOfWeek.class);
    LocalTime start = LocalTime.of(10, 0);
    LocalTime end = LocalTime.of(19, 0);
    fridayTimeSlot = new TimeSlot(start, end);
    testHours.put(DayOfWeek.FRIDAY, fridayTimeSlot);
    testItems = new HashSet<>();
    ItemId itemId = new ItemId(ItemType.FOOD, "Canned Beans");
    testItem = new Item(itemId, 10, testCenter, "2022-12-31");
    testItems.add(testItem);
  }

  /**
   * Tests the constructor to verify the name and description are correct.
   */
  @Test
  public void constructorTest() {
    StorageCenter testCenter = new StorageCenter("CUFP", "Food Pantry");
    assertEquals("CUFP", testCenter.getName());
    assertEquals("Food Pantry", testCenter.getDescription());
  }

  /**
   * Tests the constructor to verify it throws IllegalArgumentException on invalid input.
   */
  @Test
  public void constructorFailTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      new StorageCenter("", "Food Pantry");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new StorageCenter(null, "Food Pantry");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new StorageCenter("CUFP", "");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new StorageCenter("CUFP", null);
    });
  }

  /**
   * Tests the getName method to verify is the name is returned correctly.
   */
  @Test
  public void getNameTest() {
    assertEquals("CUFP", testCenter.getName());
  }

  /**
   * Tests the changeName method to verify the name is succesfully updated.
   */
  @Test
  public void updateNameTest() {
    testCenter.changeName("Food Drive");
    assertEquals("Food Drive", testCenter.getName());
  }

  /**
   * Tests the changeName method to verify it throws IllegalArgumentException on invalid input.
   */
  @Test
  public void updateNameFailTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      testCenter.changeName("");
    });
    assertEquals("CUFP", testCenter.getName());


    assertThrows(IllegalArgumentException.class, () -> {
      testCenter.changeName(null);
    });
    assertEquals("CUFP", testCenter.getName());


    assertThrows(IllegalArgumentException.class, () -> {
      testCenter.changeName(" ");
    });
    assertEquals("CUFP", testCenter.getName());
  }

  /**
   * Tests the getDescription method to verify the description is returned correctly.
   */
  @Test
  public void getDescriptionTest() {
    assertEquals("Food Pantry", testCenter.getDescription());
  }

  /**
   * Tests the changeDescription method to verify the description is successfully updated.
   */
  @Test
  public void updateDescriptionTest() {
    testCenter.changeDescription("Clothes Pantry");
    assertEquals("Clothes Pantry", testCenter.getDescription());
  }

  /**
   * Tests that the changeDescription method throws an IllegalArgumentException on invalid input.
   */
  @Test
  public void updateDescriptionFailTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      testCenter.changeDescription("");
    });
    assertEquals("Food Pantry", testCenter.getDescription());

    assertThrows(IllegalArgumentException.class, () -> {
      testCenter.changeDescription(null);
    });
    assertEquals("Food Pantry", testCenter.getDescription());

    assertThrows(IllegalArgumentException.class, () -> {
      testCenter.changeDescription(" ");
    });
    assertEquals("Food Pantry", testCenter.getDescription());
  }

  /**
   * Tests the getHours method to verify the hours are returned correctly.
   */
  @Test
  public void getHoursTest() {
    Map<DayOfWeek, TimeSlot> expectedHours = new EnumMap<>(DayOfWeek.class);
    Map<DayOfWeek, TimeSlot> testHours = testCenter.getOperatingHours();
    assertEquals(expectedHours, testHours);
  }

  /**
   * Tests the updateDayHours method to verify the hours are successfully updated.
   */
  @Test
  public void updateHoursTest() {
    LocalTime start = LocalTime.of(10, 0);
    LocalTime end = LocalTime.of(19, 0);
    fridayTimeSlot = new TimeSlot(start, end);
    testCenter.updateDayHours(fridayTimeSlot, 5);
    Map<DayOfWeek, TimeSlot> updatedHours = testCenter.getOperatingHours();
    assertEquals(fridayTimeSlot, updatedHours.get(DayOfWeek.FRIDAY));
  }

  /**
   * Tests that the updateDayHours method throws an IllegalArgumentException on invalid input.
   */
  @Test
  public void updateHoursFailTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      testCenter.updateDayHours(fridayTimeSlot, 8);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      testCenter.updateDayHours(fridayTimeSlot, 0);
    });

    Map<DayOfWeek, TimeSlot> updatedHours = testCenter.getOperatingHours();
    assertNotEquals(fridayTimeSlot, updatedHours.get(DayOfWeek.FRIDAY));
  }

  /**
   * Tests the getItems method to verify the items are returned correctly.
   */
  @Test
  public void getItemsTest() {
    testCenter.setItems(testItems);
    assertEquals(testItems, testCenter.getItems());
  }

  /**
   * Tests the printItems method to verify the items are printed correctly.
   */
  @Test
  public void printItemsTest() {
    StringBuilder result = new StringBuilder();
    result.append("Items: ").append("\n");
    for (Item item : testItems) {
      result.append(item.toString()).append("\n");
    }
    String expected = result.toString();
    testCenter.setItems(testItems);
    assertEquals(expected, testCenter.printItems());
  }

  /**
   * Tests the toString method to verify toString returns the correct string.
   */
  @Test
  public void toStringTest() {
    StringBuilder result = new StringBuilder();
    result.append("Storage Center Name: ").append(testCenter.getName()).append("\n");
    result.append("Description: ").append(testCenter.getDescription()).append("\n");
    result.append("Operating Hours: ").append("\n");
    testHours.forEach((day, hours) -> {
      result.append(day).append(": ").append(hours).append("\n");
    });
    String expected = result.toString();
    testCenter.updateDayHours(fridayTimeSlot, 5);
    assertEquals(expected, testCenter.toString());
  }

  /**
   * Tests the getExpiredItems method to verify the expired items are returned correctly.
   */
  @Test
  public void getExpiredItemsTest() {
    testCenter.setItems(testItems);
    Set<Item> expiredItems = testCenter.getExpiredItems();
    assertEquals(testItems, expiredItems);
  }

  /**
   * Tests the removeExpiredItems method to verify the expired items are removed.
   */
  @Test
  public void removeExpiredItemsTest() {
    testCenter.setItems(testItems);
    testCenter.removeExpiredItems();
    Set<Item> updatedItems = testCenter.getItems();
    assertEquals(0, updatedItems.size());
  }
}
