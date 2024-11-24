package com.smartprogrammingbaddies;

import com.smartprogrammingbaddies.event.Event;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;


/**
 * The Donation class representes a donation, including their name, type, donator of this item,
 * the date of this donation, lifespan, and its location.
 */
@Entity
public class Donations implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "donation_id")
  private int id;
  private String donationName;
  private String donationType;
  private String donator;
  private String lifeSpan;
  private String location;
  @ManyToOne
  private Event event;
  @ManyToOne
  private StorageCenter storage;

  /**
   * Constructs a new Donations item with the specific name, type, donator, lifespan and location.
   *
   * @param donationName the name of the donation.
   * @param donationType the type of the donation.
   * @param donator the donator of the donation.
   * @param lifeSpan the lifespan of the donation.
   * @param location the location of the donation.
   */
  public Donations(String donationName, String donationType,
      String donator, String lifeSpan, String location) {
    this.donationName = donationName;
    this.donationType = donationType;
    this.donator = donator;
    this.lifeSpan = lifeSpan;
    this.location = location;
  }
  /**
   * Returns the name of the donation.

   * @return the donation's name.
   */
  public String getDonationName() {
    return donationName;
  }

  /**
   * Returns the type of the donation.

   * @return the donation's type.
   */
  public String getDonationType() {
    return donationType;
  }

  /**
   * Returns the name of the donator.

   * @return donation's donator.
   */
  public String getDonator() {
    return donator;
  }

  /**
   * Returns the lifespan of the donation if it has any.
   *
   * @return the donation's lifespan.
   */
  public String getLifeSpan() {
    return lifeSpan;
  }

  /**
   * Returns the location of the donation.
   *
   * @return donation's location.
   */
  public String getLocation() {
    return location;
  }

  /**
    * Returns the event associated with the donation.
    *
    * @return the event associated with the donation
    */
  public Event getEvent() {
    return event;
  }

  /**
  * Returns the storage center associated with the donation.
  *
  * @return the storage center associated with the donation
  */
  public StorageCenter getStorage() {
    return storage;
  }

  /**
   * Returns a string representation of the donations, including their name,
   * type, donator, lifespan, and location.
   *
   * @return a string representation of the donation's details
   */
  @Override
  public String toString() {
    return "Donation Name: " + donationName + "\n"
           + "Donation Type: " + donationType + "\n"
           + "Donator: "  + donator + "\n"
           + "Lifespan: " + lifeSpan + "\n"
           + "Location: \n" + location;
  }
}
