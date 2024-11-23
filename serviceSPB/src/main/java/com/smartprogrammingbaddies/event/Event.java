package com.smartprogrammingbaddies.event;

import com.smartprogrammingbaddies.Donations;
import com.smartprogrammingbaddies.organization.Organization;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import com.smartprogrammingbaddies.utils.TimeSlot;
import com.smartprogrammingbaddies.volunteer.Volunteer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
* The Event class represents an event which can be organized by a StorageCenter.
* It contains including its name, description, date time, location,
* and the list of volunteers.
*/
@Entity
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "event_id")
  private int id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private LocalDate date;

  @Column(nullable = false)
  private String time; // Consider using LocalTime or LocalDateTime

  @Column(nullable = false)
  private String location;

  @Column(nullable = false)
  private boolean isCancelled = false;

  @ManyToOne
  @JoinColumn(name = "storage_center_id")
  private StorageCenter storageCenter;

  @ManyToOne
  @JoinColumn(name = "organizer_id", nullable = false)
  private Organization organizer;

  @ManyToMany
  @JoinTable(
          name = "event_volunteers",
          joinColumns = @JoinColumn(name = "event_id"),
          inverseJoinColumns = @JoinColumn(name = "volunteer_id")
  )
  private Set<Volunteer> volunteers = new HashSet<>();

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Donations> donations = new HashSet<>();

  protected Event() {
    // Default constructor for JPA
  }

  public Event(String name, String description, LocalDate date, String time, String location,
               StorageCenter storageCenter, Organization organizer) {
    this.name = name;
    this.description = description;
    this.date = date;
    this.time = time;
    this.location = location;
    this.storageCenter = storageCenter;
    this.organizer = organizer;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public boolean isCancelled() {
    return isCancelled;
  }

  public void cancelEvent() {
    this.isCancelled = true;
  }

  public StorageCenter getStorageCenter() {
    return storageCenter;
  }

  public void setStorageCenter(StorageCenter storageCenter) {
    this.storageCenter = storageCenter;
  }

  public Organization getOrganizer() {
    return organizer;
  }

  public void setOrganizer(Organization organizer) {
    this.organizer = organizer;
  }

  public Set<Volunteer> getVolunteers() {
    return volunteers;
  }

  public void addVolunteer(Volunteer volunteer) {
    volunteers.add(volunteer);
    volunteer.getEvents().add(this);
  }

  public void removeVolunteer(Volunteer volunteer) {
    volunteers.remove(volunteer);
    volunteer.getEvents().remove(this);
  }

  public Set<Donations> getDonations() {
    return donations;
  }

  public void addDonation(Donations donation) {
    donations.add(donation);
    donation.setEvent(this);
  }

  public void removeDonation(Donations donation) {
    donations.remove(donation);
    donation.setEvent(null);
  }

  @Override
  public String toString() {
    StringBuilder eventDetails = new StringBuilder();
    eventDetails.append("Event Name: ").append(name).append("\n")
            .append("Description: ").append(description).append("\n")
            .append("Date: ").append(date).append("\n")
            .append("Time: ").append(time).append("\n")
            .append("Location: ").append(location).append("\n")
            .append("Storage Center: ").append(storageCenter != null
                    ? storageCenter.getName() : "None").append("\n")
            .append("Organizer: ").append(organizer.getOrgName()).append("\n");

    if (!volunteers.isEmpty()) {
      eventDetails.append("Volunteers:\n");
      volunteers.forEach(volunteer -> eventDetails.append("- ")
              .append(volunteer.getName()).append("\n"));
    } else {
      eventDetails.append("No volunteers have signed up yet.\n");
    }

    return eventDetails.toString().trim();
  }
}
