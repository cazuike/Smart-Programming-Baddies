package com.smartprogrammingbaddies;

import java.io.Serializable;
import java.util.Map;

/**
* The Event class represents an event organized by a StorageCenter, 
* including its name, description, date
* time, location, and the list of volunteers.
*/
public class Event implements Serializable {

  /**
   * Constructs an Event with appropriate details.
   *
   * @param name the name of the event
   * @param description a description of the event
   * @param date the date of the event
   * @param time the time of the event
   * @param location the location of where the event takes place
   * @param organizer the StorageCenter organizing the event
   * @param listOfVolunteers a list of volunteers participating in the event (key is volunteer name)
   */
  public Event(String name, String description, String date, String time, String location, 
               StorageCenter organizer, Map<String, Volunteer> listOfVolunteers) {
    this.name = name;
    this.description = description;
    this.date = date;
    this.time = time;
    this.location = location;
    this.organizer = organizer;
    this.listOfVolunteers = listOfVolunteers;
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
  public String getDate() {
    return date;
  }

  /**
   * Updates the date of the event.
   *
   * @param newDate the new date of the event
   */
  public void updateDate(String newDate) {
    this.date = newDate;
  }

  /**
   * Gets the time of the event.
   *
   * @return the time of the event
   */
  public String getTime() {
    return time;
  }

  /**
   * Updates the time of the event.
   *
   * @param newTime the new time of the event
   */
  public void updateTime(String newTime) {
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
   * Gets the organizer of the event.
   *
   * @return the organizer of the event (StorageCenter)
   */
  public StorageCenter getOrganizer() {
    return organizer;
  }

  /**
   * Gets the list of volunteers in the event.
   *
   * @return a map of volunteers, the key is the volunteer's name and the value is Volunteer
   */
  public Map<String, Volunteer> getListOfVolunteers() {
    return listOfVolunteers;
  }

  /**
   * Adds a volunteer to the event.
   *
   * @param volunteer the Volunteer object to add
   */
  public void addVolunteer(Volunteer volunteer) {
    listOfVolunteers.put(volunteer.getName(), volunteer);
  }

  /**
   * Removes a volunteer from the event based on their name.
   *
   * @param volunteerName the name of the volunteer to remove
   */
  public void removeVolunteer(String volunteerName) {
    listOfVolunteers.remove(volunteerName);
  }

  /**
   * Gets the total count of volunteers signed up for the event.
   *
   * @return the number of volunteers
   */
  public int getVolunteerCount() {
    return this.listOfVolunteers.size();
  }

  /**
   * Returns a string representation of the event, including its name, description, date, time,
   * location, organizer, and the list of volunteer names.
   *
   * @return a string representation of the event
   */
  @Override
  public String toString() {
    StringBuilder eventDetails = new StringBuilder();
    eventDetails.append("Event Name: ").append(name).append("\n")
                  .append("Description: ").append(description).append("\n")
                  .append("Date: ").append(date).append("\n")
                  .append("Time: ").append(time).append("\n")
                  .append("Location: ").append(location).append("\n")
                  .append("Organizer: ").append(organizer.getName()).append("\n");

    if (!listOfVolunteers.isEmpty()) {
      eventDetails.append("Volunteer Names: \n");
      for (String volunteerName : listOfVolunteers.keySet()) {
        eventDetails.append("- ").append(volunteerName).append("\n");
      }
    } else {
      eventDetails.append("No volunteers signed up yet.\n");
    }      

    return eventDetails.toString();
  }

  private String name;
  private String description;
  private String date;
  private String time;
  private String location;
  private StorageCenter organizer;
  Map<String, Volunteer> listOfVolunteers;
}
