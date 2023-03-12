package at.happydog.test.registrationUtil.validator;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 Username Validator - muss noch implementiert werden

 z.b. mindestens 4 Zeichen und keine Anstößigen namen etc.
 **/

@Service
public class UsernameValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        //TODO: Regex to validate username
        return true;
    }
}
