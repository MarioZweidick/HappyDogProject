package at.happydog.test.configuration;

/**
 SetupEmailConfirmation class
 This class is for configuring the email requirements on the registration process
 **/

public class SetupEmailConfirmation {
    public static final Boolean EMAIL_CONFIRMATION_REQUIRED = true;
    public static final String EMAIL_CONFIRMATION_LINK = "http://localhost:8080/user/registration/confirm?token=";
}
