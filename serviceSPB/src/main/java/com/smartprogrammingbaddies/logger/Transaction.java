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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

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
  private LocalDateTime timestamp;

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

  protected Transaction() {
    // Default constructor for JPA
  }

  public Transaction(StorageCenter storageCenter, Item item, int quantity, String action) {
    if (storageCenter == null) {
      throw new IllegalArgumentException("Storage center cannot be null.");
    }
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity must be non-negative.");
    }
    if (action == null || action.isBlank()) {
      throw new IllegalArgumentException("Action cannot be null or blank.");
    }
    this.storageCenter = storageCenter;
    this.itemName = item.getName();
    this.itemType = String.valueOf(item.getType());
    this.quantity = quantity;
    this.action = action;
    this.timestamp = LocalDateTime.now();
  }

  // Getters and setters

  public int getId() {
    return id;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public StorageCenter getStorageCenter() {
    return storageCenter;
  }

  public void setStorageCenter(StorageCenter storageCenter) {
    this.storageCenter = storageCenter;
  }

  public String getItemName() {
    return itemName;
  }

  public String getItemType() {
    return itemType;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity must be non-negative.");
    }
    this.quantity = quantity;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    if (action == null || action.isBlank()) {
      throw new IllegalArgumentException("Action cannot be null or blank.");
    }
    this.action = action;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Transaction)) {
      return false;
    }
    Transaction other = (Transaction) obj;
    return this.id == other.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Transaction ID: " + id + "\n"
            + "Timestamp: " + timestamp + "\n"
            + "Storage Center: " + storageCenter.getName() + "\n"
            + "Item: " + itemName + " (" + itemType + ")\n"
            + "Quantity: " + quantity + "\n"
            + "Action: " + action;
  }
}

