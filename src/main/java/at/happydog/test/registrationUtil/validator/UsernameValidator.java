package at.happydog.test.registrationUtil.validator;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 UsernameValidator class

 Validates correct username with regex function 'test'
 **/

@Service
public class UsernameValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        //TODO: Regex to validate username
        return true;
    }
}
