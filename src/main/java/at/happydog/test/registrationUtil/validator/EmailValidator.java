package at.happydog.test.registrationUtil.validator;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 EmailValidator class

 Validates correct email addresses with regex function 'test'
 **/

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
}