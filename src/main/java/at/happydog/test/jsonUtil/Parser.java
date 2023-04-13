package at.happydog.test.jsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Parser {

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

            list.add(street);           //0
            list.add(streetNumber);     //1
            list.add(city);             //2
            list.add(postalCode);       //3
        }


        JSONObject resultsZero = jsonObject.getJSONArray("results").getJSONObject(0);

        // get the location object
        JSONObject location = resultsZero.getJSONObject("geometry").getJSONObject("location");

        // get the lat and lng values as Strings
        BigDecimal lat = location.getBigDecimal("lat");
        BigDecimal lng = location.getBigDecimal("lng");

        list.add(lat.toString());   //4
        list.add(lng.toString());   //5
        System.out.println(list.toString() + "___________________________________________________________________________________");
        System.out.println(list.size() + "SIZE___________________________________________________________________________________");
        return list;
    }
}
