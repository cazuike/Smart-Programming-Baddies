package com.smartprogrammingbaddies;

import java.io.Serializable;

/**
 * The Donation class representes a donation, including their name, type, donator of this item,
 * the date of this donation, lifespan, and its location.
 */

public class Donations implements Serializable {

  /**
   * Constructs a new Donations item with the specific name, type, donator, lifespan and location.

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

   * @return the donation's lifespan.
   */

  public String getLifeSpan() {
    return lifeSpan;
  }

  /**
   * Returns the location of the donation.

   * @return donation's location.
   */

  public String getLocation() {
    return location;
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

  private String donationName;
  private String donationType;
  private String donator;
  private String lifeSpan;
  private String location;
  
}
