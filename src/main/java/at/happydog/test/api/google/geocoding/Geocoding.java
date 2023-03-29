package at.happydog.test.api.google.geocoding;


import at.happydog.test.jsonUtil.Parser;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class Geocoding implements GeocodeInterface{

    private final Parser parser = new Parser();

    @Override
    public List<String> geocode(String N, String E) {

        if(ApiConstant.ACTIVATE_API) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + N + "," + E + "+&key=" + ApiConstant.API_KEY))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = null;
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return parser.parseGeolocationAddressData(response.body());

        }else{
            System.out.println("API CALLS DEACTIVATED");
            return null;
        }

    }

    @Override
    public List<BigDecimal> geocode(String address) {

        if(ApiConstant.ACTIVATE_API) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "+&key=" + ApiConstant.API_KEY))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = null;
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return parser.parseGeolocationCordData(response.body());

        }else{
            System.out.println("API CALLS DEACTIVATED");
            return null;
        }

    }
}
