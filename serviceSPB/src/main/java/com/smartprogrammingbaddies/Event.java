package com.smartprogrammingbaddies;

import java.io.Serializable;

public class Event implements Serializable {

  public Event(String name, String description, String date, String time, String location, String organizaer){
    this.name = name;
    this.description = description;
    this.date = date;
    this.time = time;
    this.location = location;
    this.organizer = organizaer;
  }

  private String name;
  private String description;
  private String date;
  private String time;
  private String location;
  private String organizer;
}
