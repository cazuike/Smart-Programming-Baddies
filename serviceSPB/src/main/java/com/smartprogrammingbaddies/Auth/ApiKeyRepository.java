package com.smartprogrammingbaddies.auth;

import com.smartprogrammingbaddies.auth.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for managing ApiKey entities.
 */
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    boolean existsByApiKey(String apiKey);
    Optional<ApiKey> findByApiKey(String apiKey);
}