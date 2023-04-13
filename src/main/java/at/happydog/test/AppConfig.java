package at.happydog.test;

import at.happydog.test.api.google.geocoding.Geocoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Geocoding geocoding() {
        return new Geocoding();
    }
}
