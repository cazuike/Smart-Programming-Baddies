package com.smartprogrammingbaddies;

import java.util.Date;

/**
 * Represents an item that can be donated. This item can be a food item, toiletries, or clothes.
 */
public class Item {
  /**
   * Constructs a new Item with the specified name, type, and quantity,
   * and sets the storage center where the item is stored and the life
   * span of the item.
   *
   * @param name          the name of the item
   * @param type          the type of the item
   * @param quantity      the quantity of the item
   * @param storageCenter the storage center where the item is stored
   * @param lifeSpan      the life span of the item
   */
  public Item(String name, ItemType type, int quantity, StorageCenter storageCenter, Date lifeSpan) {
    this.name = name;
    this.type = type;
    this.quantity = quantity;
    this.storageCenter = storageCenter;
    this.lifeSpan = lifeSpan;
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
   * Gets the type of the item.
   *
   * @return the type of the item
   */
  public ItemType getType() {
    return type;
  }

  /**
   * Sets the type of the item.
   *
   * @param type the new type of the item
   */
  public void setType(ItemType type) {
    this.type = type;
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
  public Date getLifeSpan() {
    return lifeSpan;
  }

  /**
   * Sets the life span of the item.
   *
   * @param lifeSpan the new life span of the item
   */
  public void setLifeSpan(Date lifeSpan) {
    this.lifeSpan = lifeSpan;
  }

  @Override
  public String toString() {
    return "Item:" +
        "name='" + name + ",\'" +
        ", type=" + type + ",\'" +
        ", quantity=" + quantity + ",\'" +
        ", storageCenter=" + storageCenter;
  }


  /**
   * Enum representing the type of the item.
   */
  public enum ItemType {
    FOOD,
    TOILETRIES,
    CLOTHES
  }
  private int quantity;
  private ItemType type;
  private String name;
  private StorageCenter storageCenter;
  private Date lifeSpan;
}
