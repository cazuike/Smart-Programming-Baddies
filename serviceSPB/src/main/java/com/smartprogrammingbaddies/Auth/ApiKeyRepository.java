package com.smartprogrammingbaddies.Auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing ApiKey entities.
 */
public interface ApiKeyRepository extends JpaRepository<com.smartprogrammingbaddies.Auth.ApiKey, Long> {
    boolean existsByApiKey(String apiKey);
    Optional<com.smartprogrammingbaddies.Auth.ApiKey> findByApiKey(String apiKey);
}