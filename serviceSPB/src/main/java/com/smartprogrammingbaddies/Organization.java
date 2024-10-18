package com.smartprogrammingbaddies;

import java.io.Serializable;
import java.util.Map;

/**
 * The Organization class represents an organization, including their name, their type,
 * and their volunteering schedule.
 */
public class Organization implements Serializable {

  /**
   * Constructs a new Organization with the specific name, type, schedule, and adate addded.
   * 
   * @param orgName the name of the organization.
   * @param orgType the type of the organization.
   * @param schedule the schedule of the organization.
   * @param dateAdded the date the organization was added to the system.
   */
  public Organization(String orgName, String orgType, 
      Map<String, String> schedule, String dateAdded) {
    this.orgName = orgName;
    this.orgType = orgType;
    this.schedule = schedule;
    this.dateAdded = dateAdded;
  }

  /**
   * Returns the name of the organization.
   * 
   * @return the organization's name.
   */
  public String getOrgName() {
    return orgName;
  }

  /**
   * Returns the type of the organization.
   * 
   * @return the organization's type.
   */
  public String getOrgType() {
    return orgType;
  }
  /**
   * Returns the schedule of the organization.
   * 
   * @return the organization's schedule.
   */

  public Map<String, String> getSchedule() {
    return schedule;
  }

  /**
   * Returns the date the org was added to the system.
   * 
   * @return the date the org was added.
   */
  public String getDateAdded() {
    return dateAdded;
  }

  /**
   * Updates the schedule of the organization.
   * 
   * @param newSchedule the new schedule to change the current one.
   */
  public void updateSchedule(Map<String, String> newSchedule) {
    this.schedule = newSchedule;
  }
  
  @Override
  public String toString() {
    StringBuilder scheduleString = new StringBuilder();

    for (Map.Entry<String, String> entry : schedule.entrySet()) {
      scheduleString.append("Date: ").append(entry.getKey()).append("\n")
                      .append("Times: ").append(entry.getValue()).append("\n");
    }
    return "Organization Name: " + orgName + "\n"
            + "Organizaton Type: " + orgType + "\n"
            + "Date Added: " + dateAdded + "\n"
            + "Schedule: \n" + scheduleString.toString();
  }
 
  private String orgName;
  private String orgType;
  private String dateAdded;
  private Map<String, String> schedule;

}
