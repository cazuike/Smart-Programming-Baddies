package com.smartprogrammingbaddies.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Item class.
 */
@Disabled
public class ItemUnitTests {
  private Item testItem;
  private ItemId testItemId;
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
    testItem = new Item(testItemId, 10, testCenter, null);
  }

  /**
   * Tests the constructor to verify the item
   * type, quantity, storage center, and expiration date are correct.
   */
  @Test
  public void constructorTest() {
    assertEquals(testItemId, testItem.getItemId());
    assertEquals(10, testItem.getQuantity());
    assertEquals(testCenter, testItem.getStorageCenter());
    LocalDate date = LocalDate.parse("2026-12-31");
    assertEquals(date, testItem.getExpirationDate());
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException
   * when the quantity is negative.
   */
  @Test
  public void constructorNegativeQuantityTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Item(testItemId, -1, testCenter, null);
    });
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException
   * when the storage center is null.
   */
  @Test
  public void constructorNullStorageCenterTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Item(testItemId, 10, null, null);
    });
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException
   * when the item type is null.
   */
  @Test
  public void constructorNullItemTypeTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Item(null, 10, testCenter, null);
    });
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException
   * when the expiration date is invalid.
   */
  @Test
  public void constructorInvalidExpirationDateTest() {
    assertThrows(DateTimeParseException.class, () -> {
      new Item(testItemId, 10, testCenter, null);
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
    assertEquals(testCenter, testItem.getStorageCenter());
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
}
