package com.smartprogrammingbaddies;

import java.io.Serializable;
import java.util.Map;

/**
 * The Volunteer class represents a volunteer, including their name, role,
 * date of sign-up, and volunteering schedule.
 */
public class Volunteer implements Serializable {

  /**
   * Constructs a new Volunteer with the specified name, role, date of sign-up, and schedule.
   *
   * @param name the name of the volunteer
   * @param role the volunteer role
   * @param dateSignUp the date that they signed up as a volunteer
   * @param schedule the volunteering schedule
   */
  public Volunteer(String name, String role, String dateSignUp, Map<String, String> schedule) {
    this.name = name;
    this.role = role;
    this.dateSignUp = dateSignUp;
    this.schedule = schedule;
  }

  /**
   * Returns the name of the volunteer.
   *
   * @return the volunteer's name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the role of the volunteer.
   *
   * @return the volunteer's role
   */
  public String getRole() {
    return role;
  }

  /**
   * Returns the date the volunteer signed up.
   *
   * @return the date the volunteer signed up
   */
  public String getDateSignUp() {
    return dateSignUp;
  }

  /**
   * Returns the volunteer's schedule.
   *
   * @return a Map containing the volunteer's schedule, where keys are dates and values are times
   */
  public Map<String, String> getSchedule() {
    return this.schedule;
  }

  /**
   * Updates the role of the volunteer.
   *
   * @param newRole the new role for the volunteer
   */
  public void updateRole(String newRole) {
    this.role = newRole;
  }

  /**
   * Updates the schedule of the volunteer.
   *
   * @param newSchedule the new schedule for the volunteer, as a Map of dates and times
   */
  public void updateSchedule(Map<String, String> newSchedule) {
    this.schedule = newSchedule;
  }

  /**
   * Returns a string representation of the volunteer, including their name,
   * role, date of sign-up, and current volunteering schedule.
   *
   * @return a string representation of the volunteer's details
   */
  @Override
  public String toString() {
    StringBuilder scheduleString = new StringBuilder();

    for (Map.Entry<String, String> entry : schedule.entrySet()) {
      scheduleString.append("Date: ").append(entry.getKey()).append("\n")
                      .append("Times: ").append(entry.getValue()).append("\n");
    }

    return "Volunteer: " + name + "\n"
           + "Role: " + role + "\n"
           + "Date Signed: " + dateSignUp + "\n"
           + "Schedule: \n" + scheduleString.toString();
  }

  private String name;
  private String role;
  private String dateSignUp;
  private Map<String, String> schedule;
}
