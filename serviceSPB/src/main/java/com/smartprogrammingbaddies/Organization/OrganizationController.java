package com.smartprogrammingbaddies.organization;

import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class OrganizationController {
  @Autowired
  OrganizationRepository organizationRepository;


  /**
   * Creates an organization with the given parameters.
   * @param orgName
   * @param orgType
   * @param schedule
   * @param dateAdded
   * @return A {@code ResponseEntity} A message if the Organization was successfully created
   */
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
      String message = "Organization " + savedOrganization.getDatabaseId() + " was created successfully";
      return new ResponseEntity<>(message, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Handles exceptions that occur during the creation of an organization.
   * @param orgId
   * @return A {@code ResponseEntity} A message if the Organization was successfully created
   * and a HTTP 200 response or, HTTP 500 reponse if an error occurred.
   */
  @GetMapping("/getOrganization")
  public ResponseEntity<?> getOrganization(@RequestParam("orgId") int orgId) {
    try {
      Organization organization = organizationRepository.findById(orgId).get();
      return new ResponseEntity<>(organization, HttpStatus.OK);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }
  /**
   * Updates the schedule of the organization.
   * @param orgId
   * @return A {@code ResponseEntity} A message if the Organization was successfully created
   * and a HTTP 200 response or, HTTP 500 reponse if an error occurred.
   */
  @DeleteMapping("/deleteOrganization")
  public ResponseEntity<?> deleteOrganization(@RequestParam("orgId") String orgId){
    try {
      organizationRepository.deleteById(Integer.parseInt(orgId));
      boolean deleted = !organizationRepository.existsById(Integer.parseInt(orgId));
      if(!deleted){
        String message = "Organization with Id: " + orgId + " was not delted";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
      }
      String message = "Organization with Id: " +orgId + " was deleted";
      return new ResponseEntity<>(message, HttpStatus.OK);
    } catch (NumberFormatException e) {
      return new ResponseEntity<>("Invalid Organization ID", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Handles exceptions that occur during the creation of an organization.
   * @param e
   * @return A {@code ResponseEntity} A message if the Organization was successfully created
   * and a HTTP 200 response or, HTTP 500 reponse if an error occurred.
   */
  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
