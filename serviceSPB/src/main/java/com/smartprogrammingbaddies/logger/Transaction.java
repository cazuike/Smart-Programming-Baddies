package com.smartprogrammingbaddies.logger;

import com.smartprogrammingbaddies.item.Item;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalTime;

/**
 * The TransactionLogger class is used to log transactions that occur in the StorageCenter class.
 */
@Entity
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "transaction_id")
  private int id;
  @Column(nullable = false)
  private LocalTime timestamp;
  @ManyToOne
  @JoinColumn(name = "storage_center_id", nullable = false)
  private StorageCenter storageCenter;
  @Column(nullable = false)
  private String itemName;
  @Column(nullable = false)
  private String itemType;
  @Column(nullable = false)
  private int quantity;
  @Column(nullable = false)
  private String action;

  /**
   * Transaction instantiated from a Item object.
   *
   * @param storageCenter the storage center where the transaction occurred
   * @param item the item involved in the transaction
   * @param quantity the quantity of the item involved in the transaction
   * @param action the action that occurred in the transaction
   * @throws IllegalArgumentException if the storage center is null,
   *        the item is null, or the action is null or empty
   */
  public Transaction(StorageCenter storageCenter, Item item, int quantity, String action) {
    if (storageCenter == null) {
      throw new IllegalArgumentException("Storage center must not be null.");
    }
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity must be greater than or equal to 0.");
    }
    if (item == null) {
      throw new IllegalArgumentException("Item must not be null.");
    }
    if (action == null || action.isBlank()) {
      throw new IllegalArgumentException("Action must not be null or empty.");
    }

    this.storageCenter = storageCenter;
    this.itemName = item.getName();
    this.itemType = item.getType();
    this.quantity = quantity;
    this.action = action;
    this.timestamp = LocalTime.now();
  }

  /**
   * Empty constructor needed for JPA.
   */
  public Transaction() {
    // Empty constructor needed for JPA
  }

  /**
   * Gets the database ID of the transaction.
   *
   * @return the database ID of the transaction
   */
  public int getDatabaseId() {
    return id;
  }

  /**
   * Gets the timestamp of the transaction.
   *
   * @return the timestamp of the transaction
   */
  public LocalTime getTimestamp() {
    return timestamp;
  }

  /**
   * Gets the storage center of the transaction.
   *
   * @return the storage center of the transaction
   */
  public int getStorageCenter() {
    return storageCenter.getDatabaseId();
  }

  /**
   * Gets the name of the item involved in the transaction.
   *
   * @return the name of the item involved in the transaction
   */
  public String getItemName() {
    return itemName;
  }

  /**
   * Gets the type of the item involved in the transaction.
   *
   * @return the type of the item involved in the transaction
   */
  public String getItemType() {
    return itemType;
  }

  /**
   * Gets the quantity of the item involved in the transaction.
   *
   * @return the quantity of the item involved in the transaction
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Gets the action that occurred in the transaction.
   *
   * @return the action that occurred in the transaction
   */
  public String getAction() {
    return action;
  }

  /**
   * Equals method for Transaction.
   *
   * @param obj the object to compare
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Transaction)) {
      return false;
    }
    Transaction other = (Transaction) obj;
    return this.id == other.id;
  }

  /**
   * Generates a hash code for the Transaction object.
   *
   * @return the int hash value of the Transaction object
   */
  @Override
  public int hashCode() {
    return id;
  }
}
