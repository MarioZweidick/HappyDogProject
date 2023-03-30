package at.happydog.test.api.google.geocoding;

import java.util.List;

public interface GeocodingInterface {

    List<String> geocode(String address);

}
