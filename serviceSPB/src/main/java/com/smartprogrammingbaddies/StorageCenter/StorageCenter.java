package com.smartprogrammingbaddies.storageCenter;

import com.smartprogrammingbaddies.item.Item;
import com.smartprogrammingbaddies.utils.TimeSlot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The StorageCenter class is used to store and manage goods such
 * as foods, toiletries, or clothes. It provides methods to add,
 * remove, and list items in the storage.
 */
@Entity
public class StorageCenter implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "storage_center_id")
  private int id;
  private String description;
  @Column(nullable = false)
  private String name;
  @ElementCollection
  @CollectionTable(name = "operation_hours", joinColumns = @JoinColumn(name = "storage_center_id"))
  @MapKeyColumn(name = "day_of_week")
  @Column(name = "hours_of_operation")
  private Map<DayOfWeek, TimeSlot> operationHours = new EnumMap<>(DayOfWeek.class);
  @OneToMany(mappedBy = "storageCenter", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Item> items;

  /**
   * Constructs a new StorageCenter where donated items can be tracked.
   * The storage center will contain a list of Item objects. Initially,
   * the list is empty.
   *
   * @param name the name of the storage center
   * @param description the description of the storage center
   */
  public StorageCenter(String name, String description) {
    this.name = name;
    this.description = description;
    this.items = new HashSet<Item>();
  }

  /**
   * Empty constructor needed for JPA.
   */
  public StorageCenter() {
    // Empty constructor needed for JPA
  }

  /**
   * Gets the database ID of the storage center.
   *
   * @return the database ID of the storage center
   */
  public int getDatabaseId() {
    return id;
  }

  /**
   * Adds an item to the storage.
   *
   * @param item the item to be added
   */
  public boolean checkInItem(String name, Item item) {
    item.setStorageCenter(this);
    return items.add(item);
  }

  /**
   * Removes an item from the storage.
   *
   * @param item the item to be removed
   * @return true if the item was successfully removed, false otherwise
   */
  public boolean checkOutItem(String name, Item item) {
    return items.remove(item);
  }

  /**
   * Lists all items in the storage.
   *
   * @return a list of all items in the storage
   */
  public Set<Item> getItems() {
    return items;
  }

  /**
   * Sets the list of items in the storage.
   *
   * @param itemList the list of items to be set
   */
  public void setItems(Set<Item> itemList) {
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
   * @return boolean if the name was successfully updated
   */
  public boolean changeName(String name) {
    if (name == null || name.isEmpty() || name.isBlank()) {
      return false;
    }
    this.name = name;
    return true;
  }

  /**
   * Returns the description of the storage center.
   *
   * @return the description of the storage center
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the storage center.
   *
   * @param description the new description of the storage center
   * @return boolean if the description was successfully updated
   */
  public boolean changeDescription(String description) {
    if (description == null || description.isEmpty() || description.isBlank()) {
      return false;
    }
    this.description = description;
    return true;
  }

  /**
   * Returns the operating hours of the storage center.
   *
   * @return Map of operating hours of the storage center
   */
  public Map<DayOfWeek, TimeSlot> getOperatingHours() {
    return operationHours;
  }

  /**
   * Sets the operating hours of the storage center.
   *
   * @param open LocalTime of the opening time
   * @param close LocalTime of the closing time
   * @param day DayOfWeek of the day of the week
   */
  public boolean updateDayHours(LocalTime open, LocalTime close, DayOfWeek day) {
    TimeSlot hours = new TimeSlot(open, close);
    TimeSlot addedHours = operationHours.put(day, hours);
    return addedHours != null;
  }

  /**
   * Prints all the items in the storage.
   *
   * @return a string representation of all items in the storage
   */
  public String printItems() {
    StringBuilder result = new StringBuilder();
    result.append("Items: ").append("\n");
    for (Item entry : items) {
      Item value = entry;
      result.append(value).append("\n");
    }
    return result.toString();
  }

  /**
   * Returns a string representation of the storage center, including its name,
   * description, operating hours, and the list of items.
   *
   * @return a string representation of the storage center
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("Storage Center Name: ").append(name).append("\n");
    result.append("Description: ").append(description).append("\n");
    result.append("Operating Hours: ").append("\n");
    operationHours.forEach((day, hours) -> {
      result.append(day).append(": ").append(hours).append("\n");
    });

    result.append(printItems());
    return result.toString();
  }
}