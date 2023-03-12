package at.happydog.test.registrationUtil.validator;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 Passwort Validator - muss noch implementiert werden

 z.b. Das Passwort muss 8 Zeichen haben etc.
 **/

@Service
public class PasswordValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        //TODO: Regex to validate password
        return true;
    }
}
