package com.smartprogrammingbaddies.item;

import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

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
  @Temporal(TemporalType.DATE)
  @Column(name = "expiration_date")
  private Date expirationDate;

  /**
   * Constructs a new Item with the specified name, type, and quantity,
   * and sets the storage center where the item is stored and the life
   * span of the item.
   *
   * @param itemType       the type of the item which includes the name and type
   * @param quantity       the quantity of the item
   * @param storageCenter  the storage center where the item is stored
   * @param expirationDate the item's expiration date
   */
  public Item(ItemId itemType, int quantity,
      StorageCenter storageCenter, Date expirationDate) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity must be greater than or equal to 0.");
    }

    if (expirationDate != null && expirationDate.before(new Date())) {
      throw new IllegalArgumentException("Expiration date must be in the future.");
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
    this.expirationDate = expirationDate;
  }

  /**
   * Empty constructor needed for JPA.
   */
  public Item() {
    // Empty constructor needed for JPA
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
   * Sets the name of the item.
   *
   * @param name the String new name of the item
   */
  public void setName(String name) {
    this.itemType.setName(name);
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
   * Sets the type of the item.
   *
   * @param type the new type of the item
   * @return if the type was successfully updated
   */
  public boolean setType(String type) {
    this.itemType.setType(type);
    return true;
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
   * Sets the quantity of the item.
   *
   * @param quantity the int of the new quantity of the item
   * @return if the quantity was successfully updated
   */
  public boolean setQuantity(int quantity) {
    if (quantity < 0) {
      return false;
    }
    this.quantity = quantity;
    return true;
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
   */
  public void setStorageCenter(StorageCenter storageCenter) {
    this.storageCenter = storageCenter;
  }

  /**
   * Gets the life span of the item.
   *
   * @return the life span of the item
   */
  public Date getExpirationDate() {
    return expirationDate;
  }

  /**
   * Sets the life span of the item.
   *
   * @param expirationDate the item's new expiration date
   */
  public void setExpirationDate(Date expirationDate) {
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
    result.append("Item ID:").append("\n");
    result.append(" Name: ").append(this.getName()).append("\n");
    result.append(" Type: ").append(this.getType()).append("\n");
    result.append("Quantity: ").append(quantity).append("\n");
    result.append("Storage Center: ").append(storageCenter.getName()).append("\n");
    if (expirationDate != null) {
      result.append("Expiration Date: ").append(expirationDate.toString()).append("\n");
    } else {
      result.append("Item Expiration Date: N/A\n");
    }

    return result.toString();
  }
}
