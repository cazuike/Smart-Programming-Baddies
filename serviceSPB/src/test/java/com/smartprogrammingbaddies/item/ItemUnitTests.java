package com.smartprogrammingbaddies.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Item class.
 */
public class ItemUnitTests {
  private Item testItem;
  private Item testItem2;
  private ItemId testItemId;
  private ItemId testItemId2;
  private StorageCenter testCenter;

  /**
   * The Item set up to be tested.
   *
   * @throws ParseException if the date could not be parsed
   */
  @BeforeEach
  public void setupItemForTesting() throws ParseException {
    testItemId = new ItemId(ItemId.ItemType.FOOD, "Canned Beans");
    testCenter = new StorageCenter("CUFP", "Food Pantry");
    testItem = new Item(testItemId, 10, testCenter, "2026-12-31");

    testItemId2 = new ItemId(ItemId.ItemType.FOOD, "Canned Corn");
    testItem2 = new Item(testItemId2, 5, testCenter, null);
  }

  /**
   * Tests the constructor to verify the item
   * type, quantity, storage center, and expiration date are correct.
   */
  @Test
  public void constructorTest() {
    assertEquals(testItemId, testItem.getItemId());
    assertEquals(10, testItem.getQuantity());
    assertNotNull(testItem.getStorageCenter());
    LocalDate date = LocalDate.parse("2026-12-31");
    assertEquals(date, testItem.getExpirationDate());

    assertEquals(testItemId2, testItem2.getItemId());
    assertEquals(5, testItem2.getQuantity());
    assertNotNull(testItem2.getStorageCenter());
    assertEquals(null, testItem2.getExpirationDate());
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException
   * when the quantity is negative.
   */
  @Test
  public void constructorNegativeQuantityTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Item(testItemId, -1, testCenter, "2026-12-31");
    });
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException
   * when the storage center is null.
   */
  @Test
  public void constructorNullStorageCenterTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Item(testItemId, 10, null, "2026-12-31");
    });
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException
   * when the item type is null.
   */
  @Test
  public void constructorNullItemTypeTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Item(null, 10, testCenter, "2026-12-31");
    });
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException
   * when the expiration date is invalid.
   */
  @Test
  public void constructorInvalidExpirationDateTest() {
    assertThrows(DateTimeParseException.class, () -> {
      new Item(testItemId, 10, testCenter, "ab/31/2026");
    });
  }

  /**
   * Tests the incrementQuantity method to verify the quantity is incremented correctly.
   */
  @Test
  public void incrementQuantityTest() {
    testItem.incrementQuantity(5);
    assertEquals(15, testItem.getQuantity());
  }

  /**
   * Tests the incrementQuantity method to verify it throws an IllegalArgumentException
   * when the quantity is invalid.
   */
  @Test
  public void incrementQuantityNegativeTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      testItem.incrementQuantity(-1);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      testItem.incrementQuantity(0);
    });
  }

  /**
   * Tests the decrementQuantity method to verify the quantity is decremented correctly.
   */
  @Test
  public void decrementQuantityTest() {
    testItem.decrementQuantity(5);
    assertEquals(5, testItem.getQuantity());
  }

  /**
   * Tests the decrementQuantity method to verify it throws an IllegalArgumentException
   * when the quantity is invalid.
   */
  @Test
  public void decrementQuantityNegativeTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      testItem.decrementQuantity(-1);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      testItem.decrementQuantity(0);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      testItem.decrementQuantity(11);
    });
  }

  /**
   * Tests the getExpirationDate method for correct LocalDate returned.
   */
  @Test
  public void getExpirationDateTest() {
    LocalDate date = LocalDate.parse("2026-12-31");
    assertEquals(date, testItem.getExpirationDate());
  }

  /**
   * Tests the setStorageCenter method to verify the storage center is updated correctly.
   */
  @Test
  public void setStorageCenterTest() {
    testItem.setStorageCenter(testCenter);
    assertNotNull(testItem.getStorageCenter());
  }

  /**
   * Tests the setStorageCenter method to verify it throws an IllegalArgumentException
   * when the storage center is null.
   */
  @Test
  public void setStorageCenterNullTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      testItem.setStorageCenter(null);
    });
  }

  /**
   * Tests the toString method to verify it returns the correct string.
   */
  @Test
  public void toStringTest() {
    StringBuilder result = new StringBuilder();
    result.append("\tName: ").append("Canned Beans");
    result.append("\tType: ").append("FOOD");
    result.append("\tQuantity: ").append(10);
    result.append("\tExpiration Date: ");
    LocalDate expirationDate = LocalDate.parse("2026-12-31");
    if (expirationDate != null) {
      result.append(expirationDate.toString()).append("\n");
    } else {
      result.append("N/A\n");
    }
    String expected = result.toString();
    assertEquals(expected, testItem.toString());
  }

  /**
   * Tests the equals method to verify two Item objects are equal.
   */
  @Test
  public void equalsTest() throws ParseException {
    Item testItem2 = new Item(testItemId, 10, testCenter, "2026-12-31");
    assertEquals(testItem, testItem2);
  }

  /**
   * Tests the equals method to verify two Item objects are not equal based on itemId.
   */
  @Test
  public void notEqualsTest() throws ParseException {
    ItemId testItemId2 = new ItemId(ItemId.ItemType.FOOD, "Canned Corn");
    Item testItem2 = new Item(testItemId2, 5, testCenter, "2026-12-31");
    assertEquals(false, testItem.equals(testItem2));
  }

  /**
   * Tests the equals method to verify two Item objects are not equal based on expiration date.
   */
  @Test
  public void notEqualsExpirationDateTest() throws ParseException {
    Item testItem2 = new Item(testItemId, 10, testCenter, "2026-12-30");
    assertEquals(false, testItem.equals(testItem2));
  }

  /**
   * Tests the equals method to verify two Item objects are not equal based on storage centers.
   */
  @Test
  public void notEqualsStorageCenterTest() throws ParseException {
    StorageCenter testCenter2 = new StorageCenter("CUFP", "Food Pantry");
    Item testItem2 = new Item(testItemId, 10, testCenter2, "2026-12-31");
    assertEquals(false, testItem.equals(testItem2));
  }

  /**
   * Tests the equals method with two different objects.
   */
  @Test
  public void notEqualsDifferentObjectTest() {
    assertEquals(false, testCenter.equals(null));
    assertEquals(false, testItem.equals("testObject"));
  }
}
