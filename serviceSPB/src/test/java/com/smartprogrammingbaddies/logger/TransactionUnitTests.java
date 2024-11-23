package com.smartprogrammingbaddies.logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.smartprogrammingbaddies.item.Item;
import com.smartprogrammingbaddies.item.ItemId;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import java.text.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The TransactionLogger class unit tests.
 */
public class TransactionUnitTests {
  private Transaction testTransaction;
  private StorageCenter testCenter;
  private Item testItem;
  private ItemId testItemId;

  /**
   * Sets up the tests.
   *
   * @throws ParseException if the setup fails
   */
  @BeforeEach
  public void setUp() throws ParseException {
    testCenter = new StorageCenter("Test Center", "Test");
    testItemId = new ItemId(ItemId.ItemType.FOOD, "Test Item");
    testItem = new Item(testItemId, 10, testCenter, null);
    testTransaction = new Transaction(testCenter, testItem, 10, "Check Out");
  }

  /**
   * Tests the Transaction constructor.
   */
  @Test
  public void testTransactionConstructor() {
    testTransaction = new Transaction(testCenter, testItem, 10, "Check Out");
    assertEquals("Test Item", testTransaction.getItemName());
    assertEquals("FOOD", testTransaction.getItemType());
    assertEquals(10, testTransaction.getQuantity());
    assertEquals("Check Out", testTransaction.getAction());
  }

  /**
   * Tests the Transaction constructor with invalid values.
   */
  @Test
  public void testTransactionConstructorInvalidValues() {
    assertThrows(IllegalArgumentException.class,
          () -> new Transaction(null, testItem, 10, "Check Out"));

    assertThrows(IllegalArgumentException.class,
          () -> new Transaction(testCenter, null, 10, "Check Out"));

    assertThrows(IllegalArgumentException.class,
          () -> new Transaction(testCenter, testItem, -1, "Check Out"));

    assertThrows(IllegalArgumentException.class,
          () -> new Transaction(testCenter, testItem, 10, null));

    assertThrows(IllegalArgumentException.class,
          () -> new Transaction(testCenter, testItem, 10, " "));
  }

  /**
   * Tests the getTiemstamp method.
   */
  @Test
  public void testGetTimestamp() {
    assertEquals(testTransaction.getTimestamp(), testTransaction.getTimestamp());
  }

  /**
   * Tests the getItemName method.
   */
  @Test
  public void testGetItemName() {
    assertEquals("Test Item", testTransaction.getItemName());
  }

  /**
   * Tests the getItemType method.
   */
  @Test
  public void testGetItemType() {
    assertEquals("FOOD", testTransaction.getItemType());
  }

  /**
   * Tests the getQuantity method.
   */
  @Test
  public void testGetQuantity() {
    assertEquals(10, testTransaction.getQuantity());
  }

  /**
   * Tests the getAction method.
   */
  @Test
  public void testGetAction() {
    assertEquals("Check Out", testTransaction.getAction());
  }

  /**
   * Tests the equals method.
   */
  @Test
  public void testEquals() {
    Transaction testTransaction2 = new Transaction(testCenter, testItem, 10, "Check Out");
    assertEquals(testTransaction, testTransaction2);
  }

  /**
   * Tests the equals method with different objects.
   */
  @Test
  public void testEqualsDifferentObjects() {
    assertEquals(false, testTransaction.equals("testObject"));
  }

  /**
   * Tests the hashCode method.
   */
  @Test
  public void testHashCode() {
    assertEquals(testTransaction.hashCode(), testTransaction.hashCode());
  }
}
