package com.smartprogrammingbaddies.client;

import com.smartprogrammingbaddies.organization.Organization;
import com.smartprogrammingbaddies.auth.ApiKey;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

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
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
          name = "UUID",
          strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(unique = true, nullable = false, updatable = false)
  private String clientId;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "api_key_id", referencedColumnName = "id")
  private ApiKey apiKey;

  @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
  private Organization organization;

  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Individual> individuals = new HashSet<>();

  public Client() {
    this.clientId = UUID.randomUUID().toString();
  }

  // Getters and setters

  public UUID getId() {
    return id;
  }

  public String getClientId() {
    return clientId;
  }

  public ApiKey getApiKey() {
    return apiKey;
  }

  public void setApiKey(ApiKey apiKey) {
    this.apiKey = apiKey;
    apiKey.setClient(this);
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = organization;
    if (organization != null) {
      organization.setClient(this);
    }
  }

  public Set<Individual> getIndividuals() {
    return individuals;
  }

  public void addIndividual(Individual individual) {
    individuals.add(individual);
  }

  public void removeIndividual(Individual individual) {
    individuals.remove(individual);
  }
}

