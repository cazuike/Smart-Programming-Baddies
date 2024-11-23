package com.smartprogrammingbaddies.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, Integer> {
    // Example: Find individual by email
    Individual findByEmail(String email);
}
