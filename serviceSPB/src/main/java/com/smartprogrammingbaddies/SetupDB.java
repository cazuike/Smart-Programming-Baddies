package com.smartprogrammingbaddies;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.context.annotation.Configuration;
import java.io.FileInputStream;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Configuration;
import java.io.FileInputStream;
import java.util.concurrent.CompletableFuture;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
@Configuration
public class SetupDB {

    public SetupDB() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/java/com/smartprogrammingbaddies/spbservice-40a86-firebase-adminsdk-s1wtc-bcadcaece9.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://spbservice-40a86-default-rtdb.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            System.err.println("error setting up firebase");
            e.printStackTrace();
        }
    }

    public void enrollClient(String apiKey) {
        try {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clients");
            Map<String, Object> clientData = new HashMap<>();
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

    public CompletableFuture<Boolean> verifyClient(String apiKey) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clients").child(apiKey);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println("Client with API Key " + apiKey + " exists.");
                    future.complete(true);
                } else {
                    System.out.println("No client found with API Key: " + apiKey);
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

