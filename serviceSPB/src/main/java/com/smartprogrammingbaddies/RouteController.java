package com.smartprogrammingbaddies;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains all the API routes for the system.
 */
@RestController
public class RouteController {
  /**
   * Redirects to the homepage.
   *
   * @return A String containing the name of the html file to be loaded.
   */
  @GetMapping({ "/", "/index", "/home" })
  public String index() {
    return "Welcome to the donation and volunteer management system service. The best one in town!";
  }

  /**
   * Provides exception handling for endpoint methods.
   *
   * @param e              A {@code String} representing the department.
   *
   * @return               A {@code ResponseEntity} object containing an HTTP 500
   *                       response with an appropriate error message.
   */
  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}