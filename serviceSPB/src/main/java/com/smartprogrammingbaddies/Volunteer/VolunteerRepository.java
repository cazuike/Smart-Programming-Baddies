package com.smartprogrammingbaddies.Volunteer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.smartprogrammingbaddies.Volunteer.Volunteer;

/**
 * Repository interface for managing Volunteer entities.
 */
@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {
    // Custom query methods can be added here if needed
}
