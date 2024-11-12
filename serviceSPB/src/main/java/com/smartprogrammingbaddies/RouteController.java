package com.smartprogrammingbaddies;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @Autowired
  private AuthController auth;

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
   * Register location for donations.
   *
   * @param apiKey A {@code String} representing the API key.
   * @param location A {@code String} representing registered location.
   * @return A {@code ResponseEntity} A string indicating if the location is registered or already
   *         exists in the database.
   */
  @PatchMapping("/registerLocation")
  public ResponseEntity<?> registerLocation(@RequestParam("apiKey") String apiKey,
                                           @RequestParam("name") String location) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        boolean validLocation = verifyLocation(location, apiKey).get();
        if (validLocation) {
          return new ResponseEntity<>("Location already registered.", HttpStatus.OK);
        }
        DatabaseReference ref;
        String refString = "clients/" + apiKey + "/locations";
        ref = FirebaseDatabase.getInstance().getReference(refString);
        ApiFuture<Void> future = ref.child(location).setValueAsync(null);
        future.get();
        return new ResponseEntity<>("Registered new storage center at " + location, HttpStatus.OK);
      }
      return new ResponseEntity<>("Invalid API key", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Add donation to a storage center location.
   *
   * @param apiKey A {@code String} representing the API key.
   * @param donationName A {@code String} representing the donation name.
   * @param donationType A {@code String} representing the donation type.
   * @param donator A {@code String} representing the donator.
   * @param location A {@code String} representing registered location.
   * @return A {@code ResponseEntity} A string returning the id of the donation.
   */
  @PatchMapping("/addDonation")
  public ResponseEntity<?> addDonation(@RequestParam("apiKey") String apiKey,
                                       @RequestParam("donationName") String donationName,
                                       @RequestParam("donationType") String donationType,
                                       @RequestParam("donator") String donator,
                                       @RequestParam("location") String location) {
    try {
      StringBuilder s = new StringBuilder();
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        Donations donation = new Donations(donationName, donationType, donator, "N/A", location);
        boolean validLocation = verifyLocation(location, apiKey).get();
        if (!validLocation) {
          s.append("Adding unregistered location" + location + '\n');
        }
        DatabaseReference ref;
        String donationId = UUID.randomUUID().toString();
        String refString = "clients/" + apiKey + "/locations/" + location + "/donations";
        ref = FirebaseDatabase.getInstance().getReference(refString);
        ApiFuture<Void> future = ref.child(donationId).setValueAsync(donation);
        future.get();
        s.append("Registered donation id " + donationId + " to the provided location.");
        return new ResponseEntity<>(s.toString(), HttpStatus.OK);
      }
      return new ResponseEntity<>("Invalid API key", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }


  /**
   * Returns a list of potential donations to donate to.
   *
   * @param apiKey A {@code String} representing the API key.
   * @return A {@code ResponseEntity} A string returning comma separated values of locations.
   */
  @GetMapping("/listLocations")
  public ResponseEntity<?> listLocations(@RequestParam("apiKey") String apiKey) {
    try {
      boolean validApiKey = auth.verifyApiKey(apiKey).get().getStatusCode() == HttpStatus.OK;
      if (validApiKey) {
        return new ResponseEntity<>(fetchLocations(apiKey).get(), HttpStatus.OK);
      }
      return new ResponseEntity<>("Invalid API key", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Verify if location exists in our database.
   *
   * @param apiKey A {@code String} representing the API key.
   * @param location A {@code String} representing the volunteer ID.
   * @return A {@code CompletableFuture<Boolean>} True if exists, otherwise false.
   */
  private CompletableFuture<Boolean> verifyLocation(String location, String apiKey) {
    CompletableFuture<Boolean> future = new CompletableFuture<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clients/"
            + apiKey + "/locations/").child(location);

    ref.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
          future.complete(true);
        } else {
          future.complete(false);
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        System.err.println("error:" + databaseError.getMessage());
      }
    });
    return future;
  }

  /**
   * Fetch all locations in the database.
   *
   * @param apiKey A {@code String} representing the API key.
   * @return A {@code CompletableFuture<String>} String containing all found locations.
   */
  private CompletableFuture<String> fetchLocations(String apiKey) {
    CompletableFuture<String> future = new CompletableFuture<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clients/"
            + apiKey + "/locations/");

    ref.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
          StringBuilder locations = new StringBuilder();

          for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            String location = snapshot.getKey();
            if (locations.length() > 0) {
              locations.append(", ");
            }
            locations.append(location);
          }
          future.complete(locations.toString());
        } else {
          future.complete("No Locations Found.");
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        System.err.println("error:" + databaseError.getMessage());
      }
    });
    return future;
  }

  /**
   * Provides exception handling for endpoint methods.
   *
   * @param e              A {@code String} representing the department.
   *
   * @return               A {@code ResponseEntity} object containing an HTTP 500
   *                       response with an appropriate error message.
   **/
  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}