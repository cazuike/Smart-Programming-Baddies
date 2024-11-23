package com.smartprogrammingbaddies.organization;

import com.smartprogrammingbaddies.client.Client;
import com.smartprogrammingbaddies.event.Event;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

/**
 * The Organization class represents an organization, including their name, their type,
 * and their volunteering schedule.
 */
@Entity
public class Organization implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "organization_id")
  private int id;

  @Column(nullable = false)
  private String orgName;

  @Column(nullable = false)
  private String orgType;

  @Column(nullable = false)
  private LocalDate dateAdded;

  @ElementCollection
  @CollectionTable(name = "organization_schedule", joinColumns = @JoinColumn(name = "organization_id"))
  @MapKeyColumn(name = "day_of_week")
  @Column(name = "hours_of_operation")
  private Map<DayOfWeek, String> schedule = new EnumMap<>(DayOfWeek.class);

  @OneToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<StorageCenter> storageCenters = new HashSet<>();

  @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Event> events = new HashSet<>();

  protected Organization() {
    // Default constructor for JPA
  }

  public Organization(String orgName, String orgType, LocalDate dateAdded) {
    this.orgName = orgName;
    this.orgType = orgType;
    this.dateAdded = dateAdded;
  }

  // Getters and setters

  public int getId() {
    return id;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getOrgType() {
    return orgType;
  }

  public void setOrgType(String orgType) {
    this.orgType = orgType;
  }

  public LocalDate getDateAdded() {
    return dateAdded;
  }

  public void setDateAdded(LocalDate dateAdded) {
    this.dateAdded = dateAdded;
  }

  public Map<DayOfWeek, String> getSchedule() {
    return schedule;
  }

  public void setSchedule(Map<DayOfWeek, String> schedule) {
    this.schedule = schedule;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public Set<StorageCenter> getStorageCenters() {
    return storageCenters;
  }

  public void addStorageCenter(StorageCenter storageCenter) {
    storageCenters.add(storageCenter);
    storageCenter.setOrganization(this);
  }

  public void removeStorageCenter(StorageCenter storageCenter) {
    storageCenters.remove(storageCenter);
    storageCenter.setOrganization(null);
  }

  public Set<Event> getEvents() {
    return events;
  }

  public void addEvent(Event event) {
    events.add(event);
    event.setOrganizer(this);
  }

  public void removeEvent(Event event) {
    events.remove(event);
    event.setOrganizer(null);
  }

  @Override
  public String toString() {
    StringBuilder scheduleString = new StringBuilder();
    for (Map.Entry<DayOfWeek, String> entry : schedule.entrySet()) {
      scheduleString.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }
    return "Organization Name: " + orgName + "\n"
            + "Organization Type: " + orgType + "\n"
            + "Date Added: " + dateAdded + "\n"
            + "Schedule:\n" + scheduleString.toString();
  }
}

