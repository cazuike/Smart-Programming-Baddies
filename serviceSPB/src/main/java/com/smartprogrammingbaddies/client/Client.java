package com.smartprogrammingbaddies.client;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.smartprogrammingbaddies.organization.Organization;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

/**
 * The client class represents a client, their client ID, and methods
 * to verify theior existence in the system.
 */
@Entity
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;
  private final String clientId;
  private final Set<String> clientDatabase = new HashSet<>();
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "organization_id")
  private Set<Organization> organization;

  /**
   * Constructs a new client and assigns an ID.
   */
  public Client() {
    this.clientId = UUID.randomUUID().toString();
  }

  /**
   * Returns the client's ID.

   * @return the client's ID.
   */
  public String getClientId() {
    return clientId;
  }

  /**
   * Adds client to client database.
   *
   * @param clientId the client's ID.
   */
  public String addClient(String clientId) {
    if (!clientDatabase.contains(clientId)) {
      clientDatabase.add(clientId);
      return "Client successfuly added!";
    } else {
      return "Client already exists";
    }

  }

  /**
   * Get Organization.
   *
   * @return organization the organization to get.
   */
  public Set<Organization> getOrganization() {
    return organization;
  }

  /**
   * Gets the organization that the client is associated with.

   * @return the organization that the client is associated with.
   */

  /**
   * Verifies the existence of the client.

   * @param clientId the client's ID number.
   */
  public String verifyClient(String clientId) {
    if (!clientDatabase.contains(clientId)) {
      return "Client Does Not Exist";
    } else {
      return "Client exists!";
    }
  }
}
