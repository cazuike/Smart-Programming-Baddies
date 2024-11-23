package com.smartprogrammingbaddies.storagecenter;

import com.smartprogrammingbaddies.item.Item;
import com.smartprogrammingbaddies.logger.Transaction;
import com.smartprogrammingbaddies.organization.Organization;
import com.smartprogrammingbaddies.utils.TimeSlot;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.DayOfWeek;
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

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String name;

  @ElementCollection
  @CollectionTable(name = "operation_hours", joinColumns = @JoinColumn(name = "storage_center_id"))
  @MapKeyColumn(name = "day_of_week")
  @Column(name = "hours_of_operation")
  private Map<DayOfWeek, String> operationHours = new EnumMap<>(DayOfWeek.class);

  @ManyToOne
  @JoinColumn(name = "organization_id", nullable = false)
  private Organization organization;

  @OneToMany(mappedBy = "storageCenter", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Item> items = new HashSet<>();

  @OneToMany(mappedBy = "storageCenter", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Transaction> transactions = new HashSet<>();

  protected StorageCenter() {
    // Default constructor for JPA
  }

  public StorageCenter(String name, String description) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Storage center name cannot be null or blank.");
    }
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("Storage center description cannot be null or blank.");
    }
    this.name = name;
    this.description = description;
  }

  // Getters and setters

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("Description cannot be null or blank.");
    }
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null or blank.");
    }
    this.name = name;
  }

  public Map<DayOfWeek, String> getOperationHours() {
    return operationHours;
  }

  public void setOperationHours(Map<DayOfWeek, String> operationHours) {
    this.operationHours = operationHours;
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = organization;
  }

  public Set<Item> getItems() {
    return items;
  }

  public void addItem(Item item) {
    items.add(item);
    item.setStorageCenter(this);
  }

  public void removeItem(Item item) {
    items.remove(item);
    item.setStorageCenter(null);
  }

  public Set<Transaction> getTransactions() {
    return transactions;
  }

  public void addTransaction(Transaction transaction) {
    transactions.add(transaction);
    transaction.setStorageCenter(this);
  }

  public void removeTransaction(Transaction transaction) {
    transactions.remove(transaction);
    transaction.setStorageCenter(null);
  }

  @Override
  public String toString() {
    StringBuilder operationHoursString = new StringBuilder();
    for (Map.Entry<DayOfWeek, String> entry : operationHours.entrySet()) {
      operationHoursString.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }
    return "Storage Center Name: " + name + "\n"
            + "Description: " + description + "\n"
            + "Operating Hours:\n" + operationHoursString.toString();
  }
}
