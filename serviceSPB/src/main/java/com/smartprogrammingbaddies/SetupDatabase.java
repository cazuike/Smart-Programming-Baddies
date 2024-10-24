package com.smartprogrammingbaddies;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.springframework.context.annotation.Configuration;

/**
 * This class contains the SetupDB class.
 */
@Configuration
public class SetupDatabase {

  /**
   * Sets up the Firebase database.
   */
  public SetupDatabase() throws IOException {
    String fbService = "src/main/java/com/smartprogrammingbaddies/"
            + "spbservice-40a86-firebase-adminsdk-s1wtc-bcadcaece9.json";
    File serviceFile = new File(fbService);

    if (!serviceFile.exists()) {
      fbService = "src/main/java/com/smartprogrammingbaddies/"
              + "database.json";
    }

    FileInputStream service = new FileInputStream(fbService);
    try {
      FirebaseOptions options = new FirebaseOptions.Builder()
              .setCredentials(GoogleCredentials.fromStream(service))
              .setDatabaseUrl("https://spbservice-40a86-default-rtdb.firebaseio.com/")
              .build();
      FirebaseApp.initializeApp(options);
    } catch (Exception e) {
      System.err.println("Error setting up Firebase");
      e.printStackTrace();
    } finally {
      service.close();
    }
  }

  /**
   * Enrolls a client into the database with an API Key.
   *
   * @param apiKey A {@code String} representing the client's API key.
   */
  public void enrollClient(String apiKey) {
    try {
      DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clients");
      /* TODO - fill out clientData, no new clients are added currently */
      Map<String, Object> clientData = new HashMap<>();
      clientData.put("api-key", apiKey);
      ApiFuture<Void> future = ref.child(apiKey).setValueAsync(clientData);
      future.get();
      // local printing
      System.out.println("Client enrolled successfully.");
    } catch (Exception e) {
      // local printing
      System.err.println("Error enrolling client.");
      e.printStackTrace();
    }
  }

  /**
   * Verifies a client exists in the database using the given API.
   *
   * @param apiKey A {@code String} representing the API key.
   * @return A {@code CompletableFuture} that resolves to a {@code Boolean} indicating if the client
        exists in the database.
   */
  public CompletableFuture<Boolean> verifyClient(String apiKey) {
    CompletableFuture<Boolean> future = new CompletableFuture<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clients").child(apiKey);

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
}

