package com.smartprogrammingbaddies.client;

import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing Client entities.
 */
public interface ClientRepository extends CrudRepository<Client, Integer> {
  boolean existsByApiKey(String apiKey);

  Client findByApiKey(String apiKey);
}
