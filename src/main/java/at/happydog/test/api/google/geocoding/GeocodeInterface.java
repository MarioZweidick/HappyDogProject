package at.happydog.test.api.google.geocoding;

import java.math.BigDecimal;
import java.util.List;

public interface GeocodeInterface {

    List<String> geocode(String N, String E);
    List<BigDecimal> geocode(String address);

}
