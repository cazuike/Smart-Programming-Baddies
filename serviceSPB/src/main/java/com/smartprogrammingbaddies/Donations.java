package com.smartprogrammingbaddies;

import java.io.Serializable;
import java.util.Map;
/**
 * The Donations class representes a donation, including their name, type, donator of this item,
 * the date of this donation, lifespan, and its location.
 */
public class Donations {

  public Donations(String donationName, String donationType, String donator, String lifeSpan, String location) {
    this.donationName = donationName;
    this.donationType = donationType;
    this.donator = donator;
    this.lifeSpan = lifeSpan;
    this.location = location;
  }

  public String getDonationName() {
    return donationName;
  }

  public String getDonationType() {
    return donationType;
  }

  public String getDonator() {
    return donator;
  }

  public String getLifeSpan() {
    return lifeSpan;
  }

  public String getLocation() {
    return location;
  }



  private String donationName;
  private String donationType;
  private String donator;
  private String lifeSpan;
  private String location;


  
}
