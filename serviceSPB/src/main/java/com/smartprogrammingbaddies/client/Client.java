package com.smartprogrammingbaddies.client;

import com.smartprogrammingbaddies.organization.Organization;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * The client class represents a client, their client API Key,
 * and their organization associated with them.
 */
@Entity
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "api_key")
  @Column(unique = true, nullable = false)
  private String apiKey;
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "organization_id")
  private Organization organization;

  /**
   * Empty constructor for JPA.
   */
  protected Client() {
    // Default constructor for JPA
  }

  /**
   * Constructs a new client and assigns an ID.
   */
  public Client(String apiKey) {
    this.apiKey = apiKey;
    this.organization = null;
  }

  /**
   * Get ID.
   *
   * @return id the id to get.
   */
  public int getId() {
    return id;
  }

  /**
   * Set ID.
   *
   * @param id the id to set.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Get API Key.
   *
   * @return apiKey the API key to get.
   */
  public String getApiKey() {
    return apiKey;
  }

  /**
   * Set API Key.
   *
   * @param apiKey the API key to set.
   */
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  /**
   * Get Organization.
   *
   * @return organization the organization to get.
   */
  public Organization getOrganization() {
    return organization;
  }

  /**
   * Set Organization.
   *
   * @param organization the organization to set.
   */
  public void setOrganization(Organization organization) {
    this.organization = organization;
  }
}
