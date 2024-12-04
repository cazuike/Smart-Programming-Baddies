package com.smartprogrammingbaddies.volunteer;

import com.smartprogrammingbaddies.event.Event;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The Volunteer class represents a volunteer, including their name, role,
 * date of sign-up, and volunteering schedule.
 */
@Entity
public class Volunteer implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "volunteerId")
  private int volunteerId;

  private String name;
  private String role;
  private String dateSignUp;

  @ElementCollection
  @CollectionTable(name = "Volunteer_schedule", joinColumns = @JoinColumn(name = "volunteerId"))
  @MapKeyColumn(name = "schedule_day")
  @Column(name = "schedule_time")
  private Map<String, String> schedule;

  @ManyToMany(mappedBy = "volunteers")
  private Set<Event> events = new HashSet<>();

  /**
   * Default constructor required for JPA and ObjectMapper.
   */
  public Volunteer() {
    // Default constructor for JPA and ObjectMapper
  }

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

  // Corrected Getter and Setter Methods
  public int getVolunteerId() {
    return volunteerId;
  }

  public void setVolunteerId(int volunteerId) {
    this.volunteerId = volunteerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getDateSignUp() {
    return dateSignUp;
  }

  public void setDateSignUp(String dateSignUp) {
    this.dateSignUp = dateSignUp;
  }

  public Map<String, String> getSchedule() {
    return schedule;
  }

  public void setSchedule(Map<String, String> schedule) {
    this.schedule = schedule;
  }

  public void updateRole(String newRole) {
    this.role = newRole;
  }

  public void updateSchedule(Map<String, String> newSchedule) {
    this.schedule = newSchedule;
  }

  @Override
  public String toString() {
    StringBuilder scheduleString = new StringBuilder();
    for (Map.Entry<String, String> entry : schedule.entrySet()) {
      scheduleString.append("Date: ").append(entry.getKey())
              .append(", Time: ").append(entry.getValue()).append("\n");
    }

    return "Volunteer: " + name + "\n"
            + "Role: " + role + "\n"
            + "Date Signed: " + dateSignUp + "\n"
            + "Schedule: \n" + scheduleString;
  }
}
