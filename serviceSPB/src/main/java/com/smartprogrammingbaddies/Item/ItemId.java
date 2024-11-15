package com.smartprogrammingbaddies.item;

import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * The ItemId class represents the unique identifier for an item.
 */
@Embeddable
public class ItemId {


  private ItemType type;
  private String name;

  /**
  * Empty constructor needed for JPA.
  */
  public ItemId() {
    // Empty constructor needed for JPA
  }

  /**
   * The ItemType enum represents the different types of items that can be stored.
   */
  public enum ItemType {
    FOOD,
    TOILETRIES,
    CLOTHES;

    /**
     * Converts a string to an ItemType.
     *
     * @param type the string to convert
     * @return the ItemType that corresponds to the string
     * or null if the string does not match any ItemType
     */
    public static ItemType fromString(String type) {
      for (ItemType itemType : ItemType.values()) {
        if (itemType.name().equals(type)) {
          return itemType;
        }
      }
      return null;
    }
  }

  /**
   * Constructs an ItemId with the specified type and name.
   *
   * @param type the type of the item
   * @param name the name of the item
   */
  public ItemId(ItemType type, String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name must not be null or empty.");
    }

    if (type == null) {
      throw new IllegalArgumentException("Type must not be null.");
    }

    this.type = type;
    this.name = name;
  }

  /**
   * Gets the type of the item.
   *
   * @return the type of the item
   */
  public String getType() {
    return type.toString();
  }

  /**
   * Gets the name of the item.
   *
   * @return the name of the item
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the type of the item.
   *
   * @param type the type of the item
   * @return if the type was set successfully updated
   */
  public boolean setType(String type) {
    this.type = ItemType.valueOf(type);
    return true;
  }

  /**
   * Sets the name of the item.
   *
   * @param name the name of the item
   * @return if the name was set successfully updated
   */
  public boolean setName(String name) {
    this.name = name;
    return true;
  }

  /**
  * Gets all the available item types.
  *
  * @return an array of all the available item types
  */
  public static ItemType[] getItemTypes() {
    return ItemType.values();
  }

  /**
  * Equals method to compare two ItemId objects.
  *
  * @param otherItem the other ItemId object to compare
  */
  @Override
  public boolean equals(Object otherItem) {
    if (otherItem == null || getClass() != otherItem.getClass()) {
      return false;
    }
    ItemId itemId = (ItemId) otherItem;
    return type == itemId.type && name.equals(itemId.name);
  }

  /**
  * Generates a hash code for the ItemId object.
  *
  * @return the int hash value of the ItemId object
  */
  @Override
  public int hashCode() {
    return Objects.hash(type, name);
  }
}
