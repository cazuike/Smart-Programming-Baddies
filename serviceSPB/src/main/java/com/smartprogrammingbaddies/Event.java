package com.smartprogrammingbaddies;

import java.io.Serializable;
import java.util.Map;

/**
* The Event class represents an event organized by a StorageCenter, including its name, description, 
* date, time, location, and the list of volunteers.
*/
public class Event implements Serializable {


  public Event(String name, String description, String date, String time, String location, StorageCenter organizer, Map<String, Volunteer> listOfVolunteers){
    this.name = name;
    this.description = description;
    this.date = date;
    this.time = time;
    this.location = location;
    this.organizer = organizer;
    this.listOfVolunteers = listOfVolunteers;
  }
  

  public String getName() {
    return name;
  }

  public void updateName(String newName) {
    this.name = newName;
  }

  public String getDescription() {
    return description;
  }

  public void updateDescription(String newDescription) {
    this.description = newDescription;
  }

  public String getDate() {
    return date;
  }

  public void updateDate(String newDate) {
    this.date = newDate;
  }
  public String getTime() {
    return time;
  }

  public void updateTime(String newTime) {
    this.time = newTime;
  }

  public String getLocation() {
    return location;
  }

  public void updateLocation(String newLocation) {
    this.location = newLocation;
  }

  public StorageCenter getOrganizer() {
    return organizer;
  }

  public Map<String, Volunteer> getListOfVolunteers() {
    return listOfVolunteers;
  }

  public void addVolunteer(Volunteer volunteer){
    listOfVolunteers.put(volunteer.getName(), volunteer);
  }

  public void removeVolunteer(String volunteerName) {
    listOfVolunteers.remove(volunteerName);
  }

  public int getVolunteerCount() {
    return this.listOfVolunteers.size();
  }

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
