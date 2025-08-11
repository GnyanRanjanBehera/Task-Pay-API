package com.task_pay.task_pay.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;


@Configuration
public class FirebaseConfig {

    @Value("${app.firebase-configuration-file}")
    private Resource firebaseConfig;

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        try (InputStream serviceAccount = firebaseConfig.getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            return FirebaseApp.initializeApp(options);
        }
    }
}


