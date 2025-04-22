package com.coder_crushers.clinic_management.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseAuth initialize() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("serviceKey.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId("opd-manager-4b317")
                    .build();
            System.out.println("Firebase Initialized");

            FirebaseApp.initializeApp(options);
            return FirebaseAuth.getInstance();

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
