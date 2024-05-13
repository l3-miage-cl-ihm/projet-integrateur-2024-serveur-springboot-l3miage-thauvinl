package fr.uga.l3miage.integrator.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;


import java.io.IOException;
import java.io.InputStream;

@Configuration
@Profile("!test")
public class FirebaseConfig {

        @Bean
        public FirebaseApp initializeFirebaseApp () throws IOException {
            ClassPathResource resource = new ClassPathResource("firebase-service-account.json");
            InputStream serviceAccount = resource.getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                return FirebaseApp.initializeApp(options);
            }
            return FirebaseApp.getInstance();
        }
}
