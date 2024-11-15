package com.smartprogrammingbaddies.event;

import com.smartprogrammingbaddies.organization.Organization;
import com.smartprogrammingbaddies.storageCenter.StorageCenter;
import com.smartprogrammingbaddies.volunteer.Volunteer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
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
  private String name;
  private String description;
  @Temporal(TemporalType.DATE)
  private Date date;
  @Temporal(TemporalType.TIME)
  private Date time;
  private String location;
  private boolean isCancelled;
  @OneToOne
  private StorageCenter storage;
  @ManyToOne
  private Organization organizer;
  @OneToMany
  @MapKey(name = "volunteer_id")
  private Set<Volunteer> volunteers;

  /**
   * Constructs an Event with appropriate details.
   *
   * @param name the name of the event
   * @param description a description of the event
   * @param date the date of the event
   * @param time the time of the event
   * @param location the location of where the event takes place
   * @param storage the StorageCenter where donation items are stored
   * @param organizer the Organization that is hosting the event
   * @param volunteers a list of volunteers participating in the event
   */
  public Event(String name, String description, Date date, Date time, String location,
               StorageCenter storage, Organization organizer, Set<Volunteer> volunteers) {
    this.name = name;
    this.description = description;
    this.date = date;
    this.time = time;
    this.location = location;
    this.isCancelled = false;
    this.storage = storage;
    this.organizer = organizer;
    this.volunteers = volunteers;
  }

  public Event() {
  }

  /**
   * Gets the database's primary key of the event.
   *
   * @return int value of the id of the event
   */
  public int getDatabaseId() {
    return id;
  }

  /**
   * Gets the name of the event.
   *
   * @return the name of the event
   */
  public String getName() {
    return name;
  }

  /**
   * Updates the name of the event.
   *
   * @param newName the new name of the event
   */
  public void updateName(String newName) {
    this.name = newName;
  }

  /**
   * Gets the description of the event.
   *
   * @return the description of the event
   */
  public String getDescription() {
    return description;
  }

  /**
   * Updates the description of the event.
   *
   * @param newDescription the new description of the event
   */
  public void updateDescription(String newDescription) {
    this.description = newDescription;
  }

  /**
   * Gets the date of the event.
   *
   * @return the date of the event
   */
  public Date getDate() {
    return date;
  }

  /**
   * Updates the date of the event.
   *
   * @param newDate the new date of the event
   */
  public void updateDate(Date newDate) {
    this.date = newDate;
  }

  /**
   * Gets the time of the event.
   *
   * @return the time of the event
   */
  public Date getTime() {
    return time;
  }

  /**
   * Updates the time of the event.
   *
   * @param newTime the new time of the event
   */
  public void updateTime(Date newTime) {
    this.time = newTime;
  }

  /**
   * Gets the location of the event.
   *
   * @return the location of the event
   */
  public String getLocation() {
    return location;
  }

  /**
   * Updates the location of the event.
   *
   * @param newLocation the new location of the event
   */
  public void updateLocation(String newLocation) {
    this.location = newLocation;
  }

  /**
   * Gets the storage of the event.
   *
   * @return the storage of the event (StorageCenter)
   */
  public StorageCenter getstorage() {
    return storage;
  }

  /**
   * Gets the organizer of the event.
   *
   * @return the organizer of the event (Organization)
   */
  public Organization getOrganizer() {
    return organizer;
  }

  /**
   * Updates the organizer of the event.
   *
   * @return the organizer of the event (Organization)
   */
  public boolean updateOrganizer(Organization newOrganizer) {
    this.organizer = newOrganizer;
    return true;
  }

  /*
   * Check if the event is cancelled.
   *
   * @return the boolean status of the event
   */
  public boolean isCancelled() {
    return isCancelled;
  }

  /**
   * Cancels the event.
   *
   * @return the boolean if event was successfully cancelled
   */
  public boolean cancelEvent() {
    this.isCancelled = true;
    return true;
  }

  /**
   * Gets the list of volunteers in the event.
   *
   * @return a map of volunteers, the key is the volunteer's name and the value is Volunteer
   */
  public Set<Volunteer> getListOfVolunteers() {
    return volunteers;
  }

  /**
   * Adds a volunteer to the event.
   *
   * @param volunteer the Volunteer object to add
   */
  public void addVolunteer(Volunteer volunteer) {
    volunteers.add(volunteer);
  }

  /**
   * Removes a volunteer from the event based on their name.
   *
   * @param volunteerId the name of the volunteer to remove
   */
  public void removeVolunteer(int volunteerId) {
    volunteers.remove(volunteerId);
  }

  /**
   * Gets the total count of volunteers signed up for the event.
   *
   * @return the number of volunteers
   */
  public int getVolunteerCount() {
    return this.volunteers.size();
  }

  /**
   * Returns a string representation of the event, including its name, description, date, time,
   * location, storage, and the list of volunteer names.
   *
   * @return a string representation of the event
   */
  @Override
  public String toString() {
    StringBuilder eventDetails = new StringBuilder();
    eventDetails.append("Event Name: ").append(name).append("\n")
                  .append("Description: ").append(description).append("\n")
                  .append("Date: ").append(date.toString()).append("\n")
                  .append("Time: ").append(time.toString()).append("\n")
                  .append("Location: ").append(location).append("\n")
                  .append("Storage Center: ").append(storage.getName()).append("\n")
                  .append("Organizer: ").append(organizer).append("\n");
    if (!volunteers.isEmpty()) {
      eventDetails.append("Volunteer Names: \n");
      for (Volunteer volunteer : volunteers) {
        String info = volunteer.getName() + " - " + volunteer.getDatabaseId();
        eventDetails.append("- ").append(info).append("\n");
      }
    } else {
      eventDetails.append("No volunteers have signed up yet.\n");
    }
    return eventDetails.toString();
  }
}
