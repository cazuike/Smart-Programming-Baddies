package com.smartprogrammingbaddies;

import java.util.Locale;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
}