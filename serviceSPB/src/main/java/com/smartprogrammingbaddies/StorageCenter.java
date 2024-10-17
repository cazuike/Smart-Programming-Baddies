package com.smartprogrammingbaddies;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The StorageCenter class is used to store and manage goods such
 * as foods, toiletries, or clothes. It provides methods to add,
 * remove, and list items in the storage.
 */
public class StorageCenter implements Serializable {
  /**
   * Constructs a new StorageCenter where donated items can be tracked.
   * The storage center will contain a list of Item objects. Initially,
   * the list is empty.
   *
   * @param name the name of the storage center
   */
  public StorageCenter(String name) {
    this.items = new HashMap<String, Item>();
    this.name = name;
  }

  /**
   * Constructs a new StorageCenter where donated items can be tracked.
   * The storage center will contain a list of Item objects. Takes a list
   * of items as a parameter.
   *
   * @param itemList the list of items to be added to the storage\
   * @param name the name of the storage center
   */
  public StorageCenter(Map<String, Item> itemList, String name) {
    this.items = itemList;
    this.name = name;
  }

  /**
   * Adds an item to the storage.
   *
   * @param item the item to be added
   */
  public void addItem(String name, Item item) {
    items.put(name, item);
  }

  /**
   * Removes an item from the storage.
   *
   * @param item the item to be removed
   * @return true if the item was successfully removed, false otherwise
   */
  public boolean removeItem(String name, Item item) {
    return items.remove(name, item);
  }

  /**
   * Lists all items in the storage.
   *
   * @return a list of all items in the storage
   */
  public Map<String, Item> getItems() {
    return items;
  }

  /**
   * Sets the list of items in the storage.
   *
   * @param itemList the list of items to be set
   */
  public void setItems(Map<String, Item> itemList) {
    items = itemList;
  }

  /**
   * Returns the name of the storage center.
   *
   * @return the name of the storage center
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the storage center.
   *
   * @param name the new name of the storage center
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Prints all the items in the storage.
   *
   * @return a string representation of all items in the storage
   */
  public String printItems() {
    StringBuilder result = new StringBuilder();
    result.append("Storage Center Name: ").append(name).append("\n");
    for (Map.Entry<String, Item> entry : items.entrySet()) {
      Item value = entry.getValue();
      result.append(value.toString());
    }
    return result.toString();
  }

  @Serial
  private static final long serialVersionUID = 234567L;
  private Map<String, Item> items;
  private String name;
}