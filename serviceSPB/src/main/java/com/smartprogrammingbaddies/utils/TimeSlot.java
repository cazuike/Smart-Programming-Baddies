package com.smartprogrammingbaddies.utils;

import com.google.gson.JsonObject;
import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * The TimeSlot class represents a time slot that consists of a start and end
 * time.
 */
@Embeddable
public class TimeSlot {
  private LocalTime startTime;
  private LocalTime endTime;

  /**
   * Empty constructor needed for JPA.
   */
  public TimeSlot() {
    // Empty constructor needed for JPA
  }

  /**
   * Constructs a TimeSlot with the specified start and end times.
   *
   * @param startTime the start time of the time slot
   * @param endTime   the end time of the time slot
   * @throws IllegalArgumentException if the start time is after the end time
   */
  public TimeSlot(LocalTime startTime, LocalTime endTime) {
    this.startTime = startTime;
    this.endTime = endTime;

    if (startTime.isAfter(endTime)) {
      throw new IllegalArgumentException("Start time must be before end time.");
    }
  }

  /**
   * Constructs a TimeSlot with the specified start and end times using strings.
   *
   * @param startTime the start time of the time slot
   * @param endTime   the end time of the time slot
   * @throws DateTimeParseException   if the strings cannot be parsed
   * @throws IllegalArgumentException if the start time is after the end time
   */
  public TimeSlot(String startTime, String endTime) throws DateTimeParseException {
    this.startTime = LocalTime.parse(startTime);
    this.endTime = LocalTime.parse(endTime);

    if (this.startTime.isAfter(this.endTime)) {
      throw new IllegalArgumentException("Start time must be before end time.");
    }
  }

  /**
   * Gets the start time of the time slot.
   *
   * @return the start time of the time slot
   */
  public LocalTime getStartTime() {
    return startTime;
  }

  /**
   * Gets the end time of the time slot.
   *
   * @return the end time of the time slot
   */
  public LocalTime getEndTime() {
    return endTime;
  }

  /**
   * toString method for the TimeSlot object.
   *
   * @return a string representation of the TimeSlot object
   */
  @Override
  public String toString() {
    return startTime + " - " + endTime;
  }

  /**
   * toJson method for the TimeSlot object.
   *
   * @return a JSON representation of the TimeSlot object
   */
  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    json.addProperty("start", startTime.toString());
    json.addProperty("end", endTime.toString());
    return json;
  }

  /**
   * Check if two TimeSlot objects are equal.
   *
   * @param otherSlot the other TimeSlot object to compare
   */
  @Override
  public boolean equals(Object otherSlot) {
    if (otherSlot == null || getClass() != otherSlot.getClass()) {
      return false;
    }
    TimeSlot timeSlot = (TimeSlot) otherSlot;
    boolean startTimesEqual = startTime.equals(timeSlot.startTime);
    boolean endTimesEqual = endTime.equals(timeSlot.endTime);
    return startTimesEqual && endTimesEqual;
  }

  /**
   * Get the hash code of the TimeSlot object.
   *
   * @return the hash code of the TimeSlot object
   */
  @Override
  public int hashCode() {
    return Objects.hash(startTime, endTime);
  }
}
