package at.happydog.test.registrationUtil.validator;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 Email Validator - muss noch implementiert werden
 **/

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {

        //TODO: Regex to validate email
        return true;
    }
}
