package com.smartprogrammingbaddies.auth;

import com.smartprogrammingbaddies.client.Client;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class ApiKey {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false, updatable = false)
  private String apiKey;

  @OneToOne(mappedBy = "apiKey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Client client;

  public ApiKey() {
    this.apiKey = UUID.randomUUID().toString();
  }

  // Getters and no setter for apiKey to maintain immutability
  public Long getId() {
    return id;
  }

  public String getApiKey() {
    return apiKey;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
