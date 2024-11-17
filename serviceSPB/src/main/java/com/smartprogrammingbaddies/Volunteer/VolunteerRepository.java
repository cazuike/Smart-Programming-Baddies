package com.smartprogrammingbaddies.volunteer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Volunteer entities.
 */
@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {
    // Custom query methods can be added here if needed
}
