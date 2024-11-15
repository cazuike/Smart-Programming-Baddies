package com.smartprogrammingbaddies.organization;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Client;

import com.smartprogrammingbaddies.StorageCenter.StorageCenter;
import com.smartprogrammingbaddies.event.Event;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
  @Column(name= "id")
  private int id;
  private String orgName;
  private String orgType;
  @Temporal(TemporalType.DATE)
  private Date dateAdded;
  private Set<String> schedule;
  // @OneToMany
  // private Client client;
  // ASK ABOUT THIS ONE
  @OneToOne
  private StorageCenter storage;
  @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Event> event;

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
