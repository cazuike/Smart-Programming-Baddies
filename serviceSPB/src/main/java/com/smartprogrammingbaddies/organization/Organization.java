package com.smartprogrammingbaddies.organization;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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
  private String orgName;
  private String orgType;
  @Temporal(TemporalType.DATE)
  private Date dateAdded;
  private Set<String> schedule;
  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;
  @OneToOne
  private StorageCenter storage;
  @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Event> event;

  /**
   * Empty constructor for JPA.
   */
  public Organization() {
    // empty constructor for JPA
  }

  /**
   * Constructs a new Organization with the specific name, type, schedule, and adate addded.

   * @param orgName the name of the organization.
   * @param orgType the type of the organization.
   * @param schedule the schedule of the organization.
   * @param dateAdded the date the organization was added to the system.
   */
  public Organization(String orgName, String orgType,
      Set<String> schedule, Date dateAdded) {
    this.orgName = orgName;
    this.orgType = orgType;
    this.schedule = schedule;
    this.dateAdded = dateAdded;
  }

  /**
   * Default constructor required for JPA and ObjectMapper.
   */
  public int getDatabaseId() {
    return id;
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
   * Returns the schedule of the organization.

   * @return the organization's schedule.
   */

  public Set<String> getSchedule() {
    return schedule;
  }

  /**
   * Returns the date the org was added to the system.

   * @return the date the org was added.
   */
  public Date getDateAdded() {
    return dateAdded;
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
   * Gets the events of the organization.
   *
   * @return the events of the organization
   */
  public Set<Event> getEvent() {
    return event;
  }

  /**
   * Updates the schedule of the organization.

   * @param newSchedule the new schedule to change the current one.
   */
  public void updateSchedule(Set<String> newSchedule) {
    this.schedule = newSchedule;
  }

  @Override
  public String toString() {
    StringBuilder scheduleString = new StringBuilder();

    for (String entry : schedule) {
      scheduleString.append("Date: ").append(entry).append("\n")
                      .append("Times: ").append(entry).append("\n");
    }
    return "Organization Name: " + orgName + "\n"
            + "Organizaton Type: " + orgType + "\n"
            + "Date Added: " + dateAdded + "\n"
            + "Schedule: \n" + scheduleString.toString();
  }
}
