package com.smartprogrammingbaddies.organization;

import com.smartprogrammingbaddies.item.Item;
import com.smartprogrammingbaddies.item.ItemId;
import com.smartprogrammingbaddies.item.ItemRepository;
import com.smartprogrammingbaddies.utils.DateParser;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class OrganizationController {
  @Autowired
  OrganizationRepository organizationRepository;
  
 @PostMapping("/createOrganization")
  public ResponseEntity<?> createOrganization(
      @RequestParam("orgName") String orgName,
      @RequestParam("orgType") String orgType,
      @RequestParam("schedule") Set<String> schedule,
      @RequestParam("dateAdded") String dateAdded) {
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      dateFormat.setLenient(false);
      java.util.Date utilDate = dateFormat.parse(dateAdded);
      java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
      Organization organization = new Organization(orgName, orgType, schedule, sqlDate);
      Organization savedOrganization = organizationRepository.save(organization);
      String message = "Organization " + savedOrganization.getOrgName() + " was created successfully";
      return new ResponseEntity<>(message, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }
  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
