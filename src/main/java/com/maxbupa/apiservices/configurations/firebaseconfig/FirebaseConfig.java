package com.maxbupa.apiservices.configurations.firebaseconfig;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;


@Configuration
public class FirebaseConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirebaseConfig.class);

    @Bean
    public DatabaseReference firebaseDatabse()
    {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("/firebase-authentication.json").getInputStream()))
                    .setDatabaseUrl("https://demoforhospitals.firebaseio.com/")
                    .build();
            FirebaseApp.initializeApp(options);

        } catch (IOException e) {
            LOGGER.error("IOException in FirebaseConfig.firebaseDatabse() {}",e);
        }
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        return firebase;
    }
}


