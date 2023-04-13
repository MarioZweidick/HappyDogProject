package at.happydog.test.api.google.geocoding;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**

 ApiConfiguration class

 This class stores the Google API key.

 Store the API key in a text document and change the path to your located file.

 No API key? Get an API key from Google: https://console.cloud.google.com/

 **/

public class ApiConfiguration {

    private static final Path API_KEY_PATH = Path.of("C:/Users/Asterisk/Documents/apikeys/GoogleApiKey.txt");
    public static final String API_KEY;

    static {
        try {
            API_KEY = Files.readString(API_KEY_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final Boolean ACTIVATE_API = true;
}
