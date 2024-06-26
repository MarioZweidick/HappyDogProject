package at.happydog.test.api.google.geocoding;


import at.happydog.test.jsonUtil.Parser;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;



public class Geocoding implements GeocodingInterface {

    private final Parser parser = new Parser();

    @Override
    @Bean
    public List<String> geocode(String address) throws IOException, InterruptedException {

        if(ApiConfiguration.ACTIVATE_API) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "+&key=" + ApiConfiguration.API_KEY))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = null;
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            List<String> returnFromParser = parser.parseGeolocationAddressData((response.body()));
            if(returnFromParser == null)
                return null;

            return returnFromParser;


        }else{
            System.out.println("API CALLS DEACTIVATED");
            return null;
        }

    }
}
