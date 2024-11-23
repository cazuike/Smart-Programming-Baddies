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
  protected ItemId() {
    // Empty constructor needed for JPA
  }

  /**
   * The ItemType enum represents the different types of items that can be stored.
   */
  public enum ItemType {
    FOOD,
    TOILETRIES,
    CLOTHING;

    /**
     * Converts a string to an ItemType.
     *
     * @param type the string to convert
     * @return the ItemType that corresponds to the string, null otherwise
     */
    public static ItemType fromString(String type) {
      try {
        return ItemType.valueOf(type.toUpperCase());
      } catch (IllegalArgumentException | NullPointerException e) {
        return null;
      }
    }
  }

  /**
   * Constructs an ItemId with the specified type and name.
   *
   * @param type the type of the item
   * @param name the name of the item
   */
  public ItemId(ItemType type, String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name must not be null or empty.");
    }
    if (type == null) {
      throw new IllegalArgumentException("Type must not be null.");
    }

    this.type = type;
    this.name = name;
  }

  /**
   * Constructs an ItemId with the specified type and name.
   *
   * @param type the type of the item as a String
   * @param name the name of the item
   */
  public ItemId(String type, String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name must not be null or empty.");
    }

    if (type == null || type.isBlank()) {
      throw new IllegalArgumentException("Type must not be null or empty.");
    }

    ItemType itemType = ItemType.fromString(type);

    if (itemType == null) {
      throw new IllegalArgumentException("Type must be a listed item type.");
    }

    this.type = itemType;
    this.name = name;
  }

  /**
   * Gets the type of the item.
   *
   * @return the type of the item as ItemType enum
   */
  public ItemType getType() {
    return type;
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
   * @throws IllegalArgumentException if the type is null
   */
  public void setType(ItemType type) {
    if (type == null) {
      throw new IllegalArgumentException("Type must not be null.");
    }
    this.type = type;
  }

  /**
   * Sets the name of the item.
   *
   * @param name the name of the item
   * @throws IllegalArgumentException if the name is null or empty
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name must not be null or empty.");
    }
    this.name = name;
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
   * @param obj the other object to compare
   * @return true if both ItemId objects have the same type and name, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof ItemId)) return false;
    ItemId itemId = (ItemId) obj;
    return type == itemId.type && Objects.equals(name, itemId.name);
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

