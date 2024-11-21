package com.smartprogrammingbaddies.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.smartprogrammingbaddies.item.ItemId.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ItemId class.
 */
public class ItemIdUnitTests {
  private ItemId testItemId;
  private ItemId testItemId2;
  private ItemType testType;
  private String testName;

  /**
   * The ItemId set up to be tested.
   */
  @BeforeEach
  public void setupItemIdForTesting() {
    testType = ItemType.FOOD;
    testName = "Canned Beans";
    testItemId = new ItemId(testType, testName);
    testItemId2 = new ItemId("FOOD", testName);
  }

  /**
   * Tests the constructors to verify the item type and name are correct.
   */
  @Test
  public void constructorsTest() {
    assertEquals("FOOD", testItemId.getType());
    assertEquals("Canned Beans", testItemId.getName());

    assertEquals("FOOD", testItemId2.getType());
    assertEquals("Canned Beans", testItemId2.getName());
  }

  /**
   * Tests the constructors to verify they throw IllegalArgumentException on invalid input.
   */
  @Test
  public void constructorsInvalidTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      new ItemId(" ", "Canned Beans");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new ItemId((String) null, "Canned Beans");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new ItemId("FOOD", "");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new ItemId("FOOD", null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new ItemId((ItemType) null, "Canned Beans");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new ItemId(ItemType.FOOD, "");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new ItemId(ItemType.FOOD, null);
    });
  }

  /**
   * Test the setType method to verify the item type is updated correctly.
   */
  @Test
  public void setTypeTest() {
    testItemId.setType("CLOTHING");
    assertEquals("CLOTHING", testItemId.getType());
  }

  /**
   * Tests the setType method to verify it throws an IllegalArgumentException on invalid input.
   */
  @Test
  public void setTypeInvalidTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      testItemId.setType("");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      testItemId.setType(" ");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      testItemId.setType(null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      testItemId.setType("INVALID");
    });
  }

  /**
   * Tests the setName method to verify the item name is updated correctly.
   */
  @Test
  public void setNameTest() {
    testItemId.setName("Canned Corn");
    assertEquals("Canned Corn", testItemId.getName());
  }

  /**
   * Tests the setName with invalid inputs.
   */
  @Test
  public void setNameInvalidTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      testItemId.setName("");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      testItemId.setName(" ");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      testItemId.setName(null);
    });
  }

  /**
   * Tests the getItemTypes method to verify all the available item types are returned.
   */
  @Test
  public void getItemTypesTest() {
    ItemType[] itemTypes = {ItemType.FOOD, ItemType.TOILETRIES, ItemType.CLOTHING};

    ItemType[] itemTypes2 = ItemId.getItemTypes();
    for (int i = 0; i < itemTypes.length; i++) {
      assertEquals(itemTypes[i], itemTypes2[i]);
    }
  }

  /**
   * Tests the equals method to verify it returns true when the item types and names are the same.
   */
  @Test
  public void equalsTest() {
    assertEquals(testItemId, testItemId2);
  }

  /**
   * Tests the equals method when 2 ItemId objects are not equal.
   */
  @Test
  public void notEqualsTest() {
    ItemId testItemId3 = new ItemId("TOILETRIES", "Toothpaste");
    assertEquals(false, testItemId.equals(testItemId3));

    assertEquals(false, testItemId.equals(null));

    assertEquals(false, testItemId.equals("testObject"));
  }

  /**
   * Tests the hashCode method to verify the hash code is correct.
   */
  @Test
  public void hashCodeTest() {
    assertEquals(testItemId.hashCode(), testItemId2.hashCode());
  }
}
