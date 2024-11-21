package com.smartprogrammingbaddies.item;

import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import com.smartprogrammingbaddies.utils.DateParser;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.text.ParseException;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Represents an item that can be donated. This item can be a food item, toiletries, clothes, etc.
 */
@Entity
public class Item {
  @EmbeddedId
  private ItemId itemType;
  @Column(nullable = false)
  private int quantity;
  @ManyToOne
  @JoinColumn(name = "storage_center_id", nullable = false)
  private StorageCenter storageCenter;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column(name = "expiration_date")
  private LocalDate expirationDate;

  /**
   * Constructs a new Item with the specified name, type, and quantity,
   * and sets the storage center where the item is stored and the life
   * span of the item.
   *
   * @param itemType       the type of the item which includes the name and type
   * @param quantity       the quantity of the item
   * @param storageCenter  the storage center where the item is stored
   * @param expirationDate the item's expiration date
   * @throws ParseException if the expiration date is not in the correct format
   */
  public Item(ItemId itemType, int quantity,
      StorageCenter storageCenter, String expirationDate) throws ParseException {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity must be greater than or equal to 0.");
    }

    if (storageCenter == null) {
      throw new IllegalArgumentException("Storage center must not be null.");
    }

    if (itemType == null) {
      throw new IllegalArgumentException("Item type must not be null.");
    }

    this.itemType = itemType;
    this.quantity = quantity;
    this.storageCenter = storageCenter;

    if (expirationDate == null || expirationDate.isBlank()) {
      this.expirationDate = null;
    } else {
      this.expirationDate = DateParser.stringToNumericDate(expirationDate);
    }
  }

  /**
   * Empty constructor needed for JPA.
   */
  public Item() {
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
   * @return the String name of the item
   */
  public String getName() {
    return itemType.getName();
  }

  /**
   * Gets the type of the item.
   *
   * @return the type of the item
   */
  public String getType() {
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
   * Increments the quantity of the item by the specified Quantity.
   *
   * @param quantity the Quantity to increment the quantity by
   * @throws IllegalArgumentException if the Quantity is less than 0
   */
  public void incrementQuantity(int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than 0.");
    }
    this.quantity += quantity;
  }

  /**
   * Decrements the quantity of the item by the specified quantity.
   *
   * @param quantity the quantity to decrement the quantity by
   * @throws IllegalArgumentException if the quantity is less than 0 or greater than the quantity
   */
  public void decrementQuantity(int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("quantity must be greater than 0.");
    }
    if (quantity > this.quantity) {
      throw new IllegalArgumentException("quantity must be less than or equal to the quantity.");
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
   * Gets the life span of the item.
   *
   * @return the LocalDate of the item's expiration date
   */
  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  /**
  * Returns a string representation of the item.
  *
  * @return a string representation of the item
  */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("\tName: ").append(this.getName());
    result.append("\tType: ").append(this.getType());
    result.append("\tQuantity: ").append(quantity);
    result.append("\tExpiration Date: ");
    if (expirationDate != null) {
      result.append(expirationDate.toString()).append("\n");
    } else {
      result.append("N/A\n");
    }
    return result.toString();
  }
}
