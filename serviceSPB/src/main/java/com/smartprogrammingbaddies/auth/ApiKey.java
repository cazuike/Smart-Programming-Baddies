package com.smartprogrammingbaddies.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents an API key stored in the database.
 */
@Entity
public class ApiKey {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String apiKey;

  /**
    * Default constructor for JPA.
    */
  protected ApiKey() {
    // Default constructor for JPA
  }

  /**
    * Constructs an ApiKey entity with the specified key.
    *
    * @param apiKey the API key
    */
  public ApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  /**
    * Gets the unique ID of the API key entry.
    *
    * @return the unique ID of the API key entry
    */
  public Long getId() {
    return id;
  }

  /**
    * Gets the API key.
    *
    * @return the API key
    */
  public String getApiKey() {
    return apiKey;
  }

  /**
    * Sets the API key.
    *
    * @param apiKey the new API key
    */
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  @Override
  public String toString() {
    return "ApiKey{"
            + "id=" + id
            + ", apiKey='"
            + apiKey + '\''
            + '}';
  }
}
