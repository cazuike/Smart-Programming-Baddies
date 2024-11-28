package com.smartprogrammingbaddies.organization;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smartprogrammingbaddies.client.Client;
import com.smartprogrammingbaddies.event.Event;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

/**
 * The Organization class represents an organization, including their name, their type,
 * the client associated with them, as well as the storage center and events they have.
 */
@Entity
public class Organization {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "organization_id")
  private int id;
  @Column(nullable = false)
  private String orgName;
  @Column(nullable = false)
  private String orgType;
  @Column(nullable = false)
  private boolean notificationSubscribed = false;
  @OneToOne(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
  private Client client;
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "storage_center_id")
  private StorageCenter storage;
  @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Event> events = new HashSet<>();

  /**
   * Empty constructor for JPA.
   */
  public Organization() {
    // empty constructor for JPA
  }

  /**
   * Constructs a new Organization with the specific name, type, schedule, and adate addded.
   *
   * @param orgName the name of the organization
   * @param orgType the type of the organization
   * @param client the client associated with the organization
   */
  public Organization(String orgName, String orgType, Client client) {
    if (orgName == null || orgName.isBlank()) {
      throw new IllegalArgumentException("Organization name cannot be null, empty, or blank.");
    }

    if (orgType == null || orgType.isBlank()) {
      throw new IllegalArgumentException("Organization type cannot be null, empty, or blank.");
    }

    if (client == null) {
      throw new IllegalArgumentException("Client cannot be null.");
    }

    this.orgName = orgName;
    this.orgType = orgType;
    this.client = client;
  }

  /**
   * Default constructor required for JPA and ObjectMapper.
   */
  public int getDatabaseId() {
    return id;
  }

  /**
   * sets the id of the organization.
   *
   * @param id the id of the organization.
   */
  public void setDatabaseId(int id) {
    this.id = id;
  }

  /**
   * Returns the name of the organization.

   * @return the organization's name.
   */
  public String getOrgName() {
    return orgName;
  }

  /**
   * Returns the type of the organization.

   * @return the organization's type.
   */
  public String getOrgType() {
    return orgType;
  }

  /**
   * Gets the id of the client.
   *
   * @return the id of the client
   */
  public Client getClient() {
    return client;
  }

  /**
   * Gets the storage center of the organization.
   *
   * @return the storage center of the organization
   */
  public StorageCenter getStorage() {
    return storage;
  }

  /**
   * Sets the storage center of the organization.
   *
   * @param storage the storage center to set
   */
  public void setStorage(StorageCenter storage) {
    if (storage == null) {
      throw new IllegalArgumentException("Storage center cannot be null.");
    }

    this.storage = storage;
  }

  /**
   * Gets the events of the organization.
   *
   * @return the events of the organization
   */
  public Set<Event> getEvents() {
    if (this.notificationSubscribed) {
      return events;
    } else {
      return Set.of();
    }
  }

  /**
   * Adds an event to the organization.
   *
   * @param event the event to add
   */
  public void addEvent(Event event) {
    if (event == null) {
      throw new IllegalArgumentException("Event cannot be null.");
    }

    if (this.notificationSubscribed) {
      events.add(event);
    }
  }

  /**
   * Removes an event from the organization.
   *
   * @param event the event to remove
   */
  public void removeEvent(Event event) {
    if (event == null) {
      throw new IllegalArgumentException("Event cannot be null.");
    }

    if (this.notificationSubscribed) {
      events.remove(event);
    }
  }

  /**
   * Changes the client subscription status to event notifications.
   */
  public void changeSubscriptionStatus() {
    this.notificationSubscribed = !this.notificationSubscribed;
  }

  /**
   * Gets event subscription status.
   *
   * @return the event subscription status.
   */
  public boolean getSubscriptionStatus() {
    return notificationSubscribed;
  }

  /**
   * toJson method for the Organization class.
   *
   *  @return the Organization object as a JSON string.
   */
  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    json.addProperty("id", id);
    json.addProperty("orgName", orgName);
    json.addProperty("orgType", orgType);
    json.addProperty("notificationSubscribed", notificationSubscribed);
    json.addProperty("client", client.getId());
    if (storage != null) {
      json.addProperty("storage", storage.getDatabaseId());
    }

    JsonArray jsonEvents = new JsonArray();
    for (Event event : events) {
      jsonEvents.add(event.getDatabaseId());
    }

    json.add("events", jsonEvents);

    return json;
  }

}