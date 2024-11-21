package com.smartprogrammingbaddies.auth;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing ApiKey entities.
 */
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
  boolean existsByApiKey(String apiKey);

  Optional<ApiKey> findByApiKey(String apiKey);
}