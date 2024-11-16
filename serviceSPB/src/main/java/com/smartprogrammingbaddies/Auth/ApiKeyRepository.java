package com.smartprogrammingbaddies;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing ApiKey entities.
 */
public interface ApiKeyRepository extends JpaRepository<com.smartprogrammingbaddies.ApiKey, Long> {
    boolean existsByApiKey(String apiKey);
    Optional<com.smartprogrammingbaddies.ApiKey> findByApiKey(String apiKey);
}