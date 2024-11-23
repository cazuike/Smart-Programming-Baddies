package com.smartprogrammingbaddies.item;

import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Represents an item that can be donated. This item can be a food item, toiletries, clothes, etc.
 */
@Entity
public class Item {
  @EmbeddedId
  @NotNull()
  private ItemId itemType;

  @Column(nullable = false)
  private int quantity;

  @ManyToOne
  @JoinColumn(name = "storage_center_id", nullable = false)
  @NotNull()
  private StorageCenter storageCenter;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column(name = "expiration_date")
  private LocalDate expirationDate;

  /**
   * Constructs a new Item with the specified item ID, quantity,
   * storage center, and expiration date.
   *
   * @param itemType        the unique identifier for the item
   * @param quantity        the quantity of the item
   * @param storageCenter   the storage center where the item is stored
   * @param expirationDate  the item's expiration date
   */
  public Item(@NotNull ItemId itemType, int quantity,
              @NotNull StorageCenter storageCenter, LocalDate expirationDate) {
    if (itemType == null) {
      throw new IllegalArgumentException("Item type must not be null.");
    }
    if (storageCenter == null) {
      throw new IllegalArgumentException("Storage center must not be null.");
    }
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity must be non-negative.");
    }

    this.itemType = itemType;
    this.quantity = quantity;
    this.storageCenter = storageCenter;
    this.expirationDate = expirationDate;
  }

  /**
   * Default constructor needed for JPA.
   */
  protected Item() {
    // Empty constructor needed for JPA
  }

  /**
   * Gets the item ID.
   *
   * @return the item ID
   */
  public ItemId getItemId() {
    return itemType;
  }

  /**
   * Gets the name of the item.
   *
   * @return the name of the item
   */
  public String getName() {
    return itemType.getName();
  }

  /**
   * Gets the type of the item.
   *
   * @return the type of the item as ItemType enum
   */
  public ItemId.ItemType getType() {
    return itemType.getType();
  }

  /**
   * Gets the quantity of the item.
   *
   * @return the quantity of the item
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Increments the quantity of the item by the specified amount.
   *
   * @param quantity the amount to increment the quantity by
   * @throws IllegalArgumentException if the quantity is less than or equal to 0
   */
  public void incrementQuantity(int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Increment quantity must be greater than 0.");
    }
    this.quantity += quantity;
  }

  /**
   * Decrements the quantity of the item by the specified amount.
   *
   * @param quantity the amount to decrement the quantity by
   * @throws IllegalArgumentException if the quantity is less than or equal to 0
   *                                  or greater than the current quantity
   */
  public void decrementQuantity(int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Decrement quantity must be greater than 0.");
    }
    if (quantity > this.quantity) {
      throw new IllegalArgumentException("Decrement quantity must be less than or equal to the current quantity.");
    }
    this.quantity -= quantity;
  }

  /**
   * Gets the storage center where the item is stored.
   *
   * @return the storage center where the item is stored
   */
  public StorageCenter getStorageCenter() {
    return storageCenter;
  }

  /**
   * Sets the storage center where the item is stored.
   *
   * @param storageCenter the new storage center where the item is stored
   * @throws IllegalArgumentException if the storage center is null
   */
  public void setStorageCenter(StorageCenter storageCenter) {
    if (storageCenter == null) {
      throw new IllegalArgumentException("Storage center must not be null.");
    }
    this.storageCenter = storageCenter;
  }

  /**
   * Gets the expiration date of the item.
   *
   * @return the expiration date of the item
   */
  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  /**
   * Sets the expiration date of the item.
   *
   * @param expirationDate the new expiration date of the item
   */
  public void setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
  }

  /**
   * Returns a string representation of the item.
   *
   * @return a string representation of the item
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("Name: ").append(this.getName()).append("\t");
    result.append("Type: ").append(this.getType()).append("\t");
    result.append("Quantity: ").append(quantity).append("\t");
    result.append("Expiration Date: ");
    if (expirationDate != null) {
      result.append(expirationDate.toString()).append("\n");
    } else {
      result.append("N/A\n");
    }
    return result.toString();
  }
}
