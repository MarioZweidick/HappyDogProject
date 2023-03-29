package at.happydog.test.jsonUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public List<BigDecimal> parseGeolocationCordData(String json){

        List<BigDecimal> list = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);

        JSONObject results = jsonObject.getJSONArray("results").getJSONObject(0);

        // get the location object
        JSONObject location = results.getJSONObject("geometry").getJSONObject("location");

        // get the lat and lng values as Strings
        BigDecimal lat = location.getBigDecimal("lat");
        BigDecimal lng = location.getBigDecimal("lng");

        list.add(lat);
        list.add(lng);


        return list;
    }

    public List<String> parseGeolocationAddressData(String json){

        List<String> list = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray results = jsonObject.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            JSONArray addressComponents = result.getJSONArray("address_components");

            String streetNumber = "";
            String street = "";
            String city = "";
            String country = "";
            String postalCode = "";

            for (int j = 0; j < addressComponents.length(); j++) {
                JSONObject component = addressComponents.getJSONObject(j);
                JSONArray types = component.getJSONArray("types");

                if (types.toString().contains("street_number")) {
                    streetNumber = component.getString("long_name");
                } else if (types.toString().contains("route")) {
                    street = component.getString("long_name");
                } else if (types.toString().contains("locality")) {
                    city = component.getString("long_name");
                } else if (types.toString().contains("country")) {
                    country = component.getString("long_name");
                }else if (types.toString().contains("postal_code")) {
                    postalCode = component.getString("long_name");
                }
            }

            list.add(street);
            list.add(streetNumber);
            list.add(city);
            list.add(postalCode);
            list.add(country);
        }
        return list;
    }
}
