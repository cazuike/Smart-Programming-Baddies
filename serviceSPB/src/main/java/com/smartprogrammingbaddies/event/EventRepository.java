package com.smartprogrammingbaddies.event;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * The EventRepository interface extends the CrudRepository interface
 * to provide CRUD operations for the Event class.
 */
public interface EventRepository extends CrudRepository<Event, Integer> {
  List<Event> findByDate(String date);
  
  List<Event> findByLocation(String location);
}
