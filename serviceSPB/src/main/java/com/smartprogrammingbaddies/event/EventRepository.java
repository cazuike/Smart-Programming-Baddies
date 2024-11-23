package com.smartprogrammingbaddies.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * The EventRepository interface extends JpaRepository to provide CRUD operations for the Event entity.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

  /**
   * Finds all events occurring on the specified date.
   *
   * @param date the date to search for events
   * @return a list of events on the specified date
   */
  List<Event> findByDate(LocalDate date);

  /**
   * Finds all events at the specified location.
   *
   * @param location the location to search for events
   * @return a list of events at the specified location
   */
  List<Event> findByLocation(String location);
}
