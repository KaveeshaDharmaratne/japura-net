package com.japura.forum.forum_api.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;

@Configuration
public class FirebaseConfig {

    private final String projectId = "japura-net";

    @Bean
    public FirebaseApp firebaseApp() throws IOException {

        GoogleCredentials googleCredentials = GoogleCredentials.getApplicationDefault();
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .setProjectId(projectId)
                //.setDatabaseUrl("https://japura-net-default-rtdb.firebaseio.com/")
                //.setStorageBucket("japura-net.firebasestorage.app")
                .build();

        // Check if the firebase app is already initialized
        if (FirebaseApp.getApps().isEmpty()) {

            return FirebaseApp.initializeApp(options);

        } else {

            return FirebaseApp.getInstance();
        }

    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }

    @Bean
    public Firestore firestore(FirebaseApp firebaseApp) {
        return FirestoreClient.getFirestore(firebaseApp);
    }

}
