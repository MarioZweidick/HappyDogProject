package at.happydog.test.registrationUtil.validator;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 PasswordValidator class

 Validates correct password with regex function 'test'
 **/

@Service
public class PasswordValidator implements Predicate<String> {
    @Override
    public boolean test(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
