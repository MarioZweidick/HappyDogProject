package at.happydog.test.registrationUtil.validator;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 PasswordValidator class

 Validates correct password with regex function 'test'
 **/

@Service
public class PasswordValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        //TODO: Regex to validate password
        return true;
    }
}
