package com.smartprogrammingbaddies.client;

import com.smartprogrammingbaddies.organization.Organization;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * The client class represents a client, their client ID, and methods
 * to verify theior existence in the system.
 */
@Entity
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;
  private String clientId;
  private Set<String> clientDatabase = new HashSet<>();
  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
  private Organization organization;


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

   * @param clientId the client's ID.
   */
  public void addClient(String clientId) {
    if (!clientDatabase.contains(clientId)) {
      clientDatabase.add(clientId);
      System.out.println("Client successfuly added: " + clientId);
    } else {
      System.out.println("Client already exists: " + clientId);
    }
    
  }

  /**
   * Verifies the existence of the client.

   * @param clientId the client's ID number.
   */
  public void verifyClient(String clientId) {
    if (!clientDatabase.contains(clientId)) {
      System.out.println("Client Does Not Exist" + clientId);
    } else {
      System.out.println("Client exists!" + clientId);
    }
  }
}
