package at.happydog.test.service;

import at.happydog.test.email.EmailSender;
import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.AppUserRoles;
import at.happydog.test.registrationUtil.token.ConfirmationToken;
import at.happydog.test.registrationUtil.token.ConfirmationTokenService;
import at.happydog.test.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
/**
 Service Klasse für die gesamte Business Logic des Users

 Implementiert UserDetailsService - wichtig für Spring Security und den SecurityContextHolder
 **/

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "Username not found: ";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ConfirmationTokenService confirmationTokenService;

    private final EmailSender emailSender;


    //Kommt von UserDetailsService - bei jeder Authentication wird über diese Methode abgefragt ob der user
    //exisitert
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    //Findet AppUser in Datenbank
    public AppUser findAppUserByUsername(String username){
        if(appUserRepository.findByUsername(username).isPresent())
            return appUserRepository.findByUsername(username).get();

        return null;
    }

    //Spicy Shit - registriert den Benutzer mit Token
    public String singUpUser(AppUser appUser) {
       boolean userEmailExists = appUserRepository.findByEmail(appUser.getEmail())
                .isPresent();
        boolean userUsernameExists = appUserRepository.findByUsername(appUser.getUsername())
                .isPresent();

        //Random Token für Email-Confirmation
        String token = UUID.randomUUID().toString();

        if(userEmailExists || userUsernameExists) {

            //Get existing user from database and check if it is enabled
            AppUser existingUser = appUserRepository.findByEmail(appUser.getEmail()).get();

            if(existingUser.isEnabled()){

                //TODO Endpoint - User Registration
                //throw new IllegalStateException("Username or Email already taken");
                return "Username or Email already taken!";
            }


            //Implementation um, falls der Benutzer einen neuen Token anfordert, der alte Token gelöscht wird
            confirmationTokenService.deleteConfirmationTokenByAppUserId(existingUser.getAppuser_id());

            AppUser existingAppUser = appUserRepository.findByUsername(appUser.getUsername()).get();

            //Confirmation Token ist 15 Minuten viable
            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    existingAppUser
            );
            confirmationTokenService.saveConfirmationToken(confirmationToken);


            //Link für den MailSender wenn der Benutzer den Account aktiviert
            //Alle diese Links werden noch auf Konstante ausgelagert
            String link = "http://localhost:8080/user/registration/confirm?token=" + token;
            emailSender.send(
                    appUser.getEmail(),
                    buildEmail(appUser.getUsername(), link));


            //TODO Endpoint - User Registration
            return "resending confirmation Email with new token: " + token;
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        //Unschöne Abfrage welche Role der Benutzer hat und setzt damit dann die Role
        if(appUser.getRole().toString() == "DOG_TRAINER"){
            appUser.setRole(AppUserRoles.DOG_TRAINER);
        }
        if(appUser.getRole().toString() == "DOG_OWNER"){
            appUser.setRole(AppUserRoles.DOG_OWNER);
        }

        //Speichert User in DB
        appUserRepository.save(appUser);

        //Der Grund wieso der Code zweimal exisitert ist einmal falls der Benutzer den Token nochmal anfordert
        //und einmal falls der Benutzer den Token zum ersten mal anfordert
        //Hab noch keine Möglichkeit gefunden das schöner zu machen
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:8080/user/registration/confirm?token=" + token;
        emailSender.send(
                appUser.getEmail(),
                buildEmail(appUser.getUsername(), link));


        //TODO Endpoint - User Registration
        //Instead of token redirect to confirmation age with req param username (to find in Database
        return token;
    }

    //Bestätigt User Account
    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }


    //Die Email die gesendet wird
    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
