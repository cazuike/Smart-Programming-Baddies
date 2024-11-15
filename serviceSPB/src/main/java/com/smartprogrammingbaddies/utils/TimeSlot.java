package com.smartprogrammingbaddies.utils;

import java.time.LocalTime;

import jakarta.persistence.Embeddable;

/**
 * The TimeSlot class represents a time slot for a date or day of the week.
 */
@Embeddable
public class TimeSlot {

  private LocalTime startTime;
  private LocalTime endTime;

  /**
   * Empty constructor needed for JPA.
   */
  public TimeSlot() {}

  /**
   * Constructs a TimeSlot with the specified start and end times.
   *
   * @param startTime the start time of the time slot
   * @param endTime the end time of the time slot
   */
  public TimeSlot(LocalTime startTime, LocalTime endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
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
  * Check if two TimeSlot objects are equal.
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
}
