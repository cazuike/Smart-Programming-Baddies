package com.smartprogrammingbaddies.Item;

import com.smartprogrammingbaddies.StorageCenter.StorageCenter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(nullable = false)
  private int quantity;
  @Column(nullable = false)
  private String type;
  @Column(nullable = false)
  private String name;
  @ManyToOne
  @JoinColumn(name = "storage_center_id")
  private StorageCenter storageCenter;
  @Temporal(TemporalType.DATE)
  @Column(name = "expiration_date")
  private Date ExpirationDate;

  /**
   * Constructs a new Item with the specified name, type, and quantity,
   * and sets the storage center where the item is stored and the life
   * span of the item.
   *
   * @param name           the name of the item
   * @param type           the type of the item
   * @param quantity       the quantity of the item
   * @param storageCenter  the storage center where the item is stored
   * @param ExpirationDate the item's expiration date
   */
  public Item(String name, int quantity, String type,
      StorageCenter storageCenter, Date ExpirationDate) {
    this.name = name;
    this.quantity = quantity;
    this.type = type;
    this.storageCenter = storageCenter;
    this.ExpirationDate = ExpirationDate;
  }

  /**
   * Gets the name of the item.
   *
   * @return the String name of the item
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the item.
   *
   * @param name the String new name of the item
   */
  public void setName(String name) {
    this.name = name;
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
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * Gets the type of the item.
   *
   * @return the type of the item
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the type of the item.
   *
   * @param type the new type of the item
   */
  public void setType(String type) {
    this.type = type;
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
    return ExpirationDate;
  }

  /**
   * Sets the life span of the item.
   *
   * @param ExpirationDate the new life span of the item
   */
  public void setExpirationDate(Date ExpirationDate) {
    this.ExpirationDate = ExpirationDate;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("Item ID:").append(id).append("\n");
    result.append("Item Name: ").append(name).append("\n");
    result.append("Item Type: ").append(type).append("\n");
    result.append("Item Quantity: ").append(quantity).append("\n");
    result.append("Item Storage Center: ").append(storageCenter.getName()).append("\n");
    if (ExpirationDate != null) {
      result.append("Item Expiration Date: ").append(ExpirationDate.toString()).append("\n");
    }
    else {
      result.append("Item Expiration Date: N/A\n");
    }
    return result.toString();
  }
}
