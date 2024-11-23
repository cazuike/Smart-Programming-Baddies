package com.smartprogrammingbaddies.volunteer;

import com.smartprogrammingbaddies.event.Event;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumMap;
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
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String role;

  @Column(nullable = false)
  private LocalDate dateSignUp;

  @ElementCollection
  @CollectionTable(name = "volunteer_schedule", joinColumns = @JoinColumn(name = "volunteer_id"))
  @MapKeyColumn(name = "day_of_week")
  @Column(name = "availability")
  private Map<DayOfWeek, String> schedule = new EnumMap<>(DayOfWeek.class);

  @ManyToMany(mappedBy = "volunteers")
  private Set<Event> events = new HashSet<>();

  protected Volunteer() {
    // Default constructor for JPA
  }

  public Volunteer(String name, String role, LocalDate dateSignUp) {
    this.name = name;
    this.role = role;
    this.dateSignUp = dateSignUp;
  }

  // Getters and setters

  public int getId() {
    return id;
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

  public LocalDate getDateSignUp() {
    return dateSignUp;
  }

  public void setDateSignUp(LocalDate dateSignUp) {
    this.dateSignUp = dateSignUp;
  }

  public Map<DayOfWeek, String> getSchedule() {
    return schedule;
  }

  public void setSchedule(Map<DayOfWeek, String> schedule) {
    this.schedule = schedule;
  }

  public Set<Event> getEvents() {
    return events;
  }

  public void addEvent(Event event) {
    events.add(event);
    event.getVolunteers().add(this);
  }

  public void removeEvent(Event event) {
    events.remove(event);
    event.getVolunteers().remove(this);
  }

  @Override
  public String toString() {
    StringBuilder scheduleString = new StringBuilder();
    for (Map.Entry<DayOfWeek, String> entry : schedule.entrySet()) {
      scheduleString.append("Date: ").append(entry.getKey())
              .append(", Time: ").append(entry.getValue()).append("\n");
    }

    return "Volunteer: " + name + "\n"
            + "Role: " + role + "\n"
            + "Date Signed: " + dateSignUp + "\n"
            + "Schedule: \n" + scheduleString;
  }
}
