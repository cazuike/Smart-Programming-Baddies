package com.smartprogrammingbaddies;

import com.smartprogrammingbaddies.client.Individual;
import com.smartprogrammingbaddies.event.Event;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * The Donation class represents a donation, including its name, type, donator,
 * lifespan, location, associated event, storage center, and the individual who made the donation.
 */
@Entity
public class Donations implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "donation_id")
  private int id;

  @Column(nullable = false)
  private String donationName;

  @Column(nullable = false)
  private String donationType;

  @Column(nullable = false)
  private String donator;

  @Column(nullable = true)
  private String lifeSpan;

  @Column(nullable = false)
  private String location;

  @ManyToOne
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @ManyToOne
  @JoinColumn(name = "storage_center_id", nullable = false)
  private StorageCenter storageCenter;

  // **New Field Added:**
  @ManyToOne
  @JoinColumn(name = "individual_id", nullable = false)
  private Individual individual;

  protected Donations() {
    // Default constructor for JPA
  }

  public Donations(String donationName, String donationType,
                   String donator, String lifeSpan, String location,
                   Event event, StorageCenter storageCenter, Individual individual) {
    this.donationName = donationName;
    this.donationType = donationType;
    this.donator = donator;
    this.lifeSpan = lifeSpan;
    this.location = location;
    this.event = event;
    this.storageCenter = storageCenter;
    this.individual = individual;
  }

  // Getters and setters

  public int getId() {
    return id;
  }

  public String getDonationName() {
    return donationName;
  }

  public void setDonationName(String donationName) {
    this.donationName = donationName;
  }

  public String getDonationType() {
    return donationType;
  }

  public void setDonationType(String donationType) {
    this.donationType = donationType;
  }

  public String getDonator() {
    return donator;
  }

  public void setDonator(String donator) {
    this.donator = donator;
  }

  public String getLifeSpan() {
    return lifeSpan;
  }

  public void setLifeSpan(String lifeSpan) {
    this.lifeSpan = lifeSpan;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  public StorageCenter getStorageCenter() {
    return storageCenter;
  }

  public void setStorageCenter(StorageCenter storageCenter) {
    this.storageCenter = storageCenter;
  }

  public Individual getIndividual() {
    return individual;
  }

  public void setIndividual(Individual individual) {
    this.individual = individual;
  }

  @Override
  public String toString() {
    return "Donation Name: " + donationName + "\n"
            + "Donation Type: " + donationType + "\n"
            + "Donator: " + donator + "\n"
            + "Lifespan: " + lifeSpan + "\n"
            + "Location: " + location + "\n"
            + "Event: " + event.getName() + "\n"
            + "Storage Center: " + storageCenter.getName() + "\n";
  }
}
