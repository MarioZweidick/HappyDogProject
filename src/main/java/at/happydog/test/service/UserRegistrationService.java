package at.happydog.test.service;

import at.happydog.test.enity.AppUser;
import at.happydog.test.exception.custom.AppUserException;
import at.happydog.test.registrationUtil.UserRegistrationRequest;
import at.happydog.test.registrationUtil.token.ConfirmationToken;
import at.happydog.test.registrationUtil.token.ConfirmationTokenService;
import at.happydog.test.registrationUtil.validator.EmailValidator;
import at.happydog.test.registrationUtil.validator.PasswordValidator;
import at.happydog.test.registrationUtil.validator.UsernameValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


/**
 UserRegistrationService class

 This class handles business logic for the registration process
 **/

@Service
@AllArgsConstructor
public class UserRegistrationService {

    private final AppUserService appUserService;

    private UsernameValidator usernameValidator;
    private EmailValidator emailValidator;
    private PasswordValidator passwordValidator;

    private final ConfirmationTokenService confirmationTokenService;

    public String register(UserRegistrationRequest request){

        //Validatoren für Registration Prozess

        boolean usernameIsValid = usernameValidator.test(request.getUsername());
        boolean emailIsValid = emailValidator.test(request.getEmail());
        boolean passwordIsValid = passwordValidator.test(request.getPassword());

        if(!usernameIsValid || !emailIsValid) {
            throw new AppUserException("Email oder Benutzer nicht erlaubt!");
        }

        if(!passwordIsValid) {
            throw new AppUserException("Das Passwort muss mindestens 8 Zeichen, 1 Großbuchstaben und 1 Zahl haben!");
        }

        try {
            String token = appUserService.singUpUser(new AppUser(
                    request.getUsername(),
                    request.getFirstname(),
                    request.getLastname(),
                    request.getEmail(),
                    request.getPassword(),
                    request.getRole()
            ));

            return token;
        }catch (AppUserException ex){
            throw new AppUserException(ex.getErrorMessage());
        }

    }

    //Checkt Token und bestätigt wenn viable (Token expired nach 15min)
    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found"));

        if(confirmationToken.getConfirmedAt() != null){
            //TODO return redirect to error page -> Email already confirmed
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            confirmationTokenService.deleteConfirmationToken(confirmationTokenService.getToken(token).get());
            throw new IllegalStateException("token is expired");
        }

        confirmationTokenService.setConfirmedAt(token);

        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
