package at.happydog.test.service;

import at.happydog.test.Handler.ImageHandler;
import at.happydog.test.email.EmailConfiguration;
import at.happydog.test.email.EmailSender;
import at.happydog.test.enity.*;
import at.happydog.test.exception.custom.AppUserException;
import at.happydog.test.imageUtil.ImageUtil;
import at.happydog.test.registrationUtil.token.ConfirmationToken;
import at.happydog.test.registrationUtil.token.ConfirmationTokenService;
import at.happydog.test.repository.AppUserImageRepository;
import at.happydog.test.repository.AppUserRepository;
import at.happydog.test.repository.LocationRepository;
import at.happydog.test.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 AppUserService class

 Implements UserDetailsService for SecurityContextHolder (Sring Security)

 This class handles business logic for the AppUser and receives data through the repositories
 **/

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "Username not found: ";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ConfirmationTokenService confirmationTokenService;

    private final EmailSender emailSender;
    private final AppUserImageRepository appUserImageRepository;

    private final TrainingRepository trainingRepository;
    private final LocationRepository locationRepository;
    private final LocationService locationService;


    //Implemented with UserDetailsService. Every user request will be checked with this.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }


    //Finds AppUser by id in the database
    public AppUser findAppUserById(Long id) throws AppUserException{
        if(appUserRepository.findById(id).isPresent())
            return appUserRepository.findById(id).get();
        else
            throw new AppUserException("Fehler: Benutzer wurde nicht gefunden!");

    }

    //Registers the user and sends EmailConfirmationToken
    public String singUpUser(AppUser appUser) throws AppUserException {
       boolean userEmailExists = appUserRepository.findByEmail(appUser.getEmail())
                .isPresent();
        boolean userUsernameExists = appUserRepository.findByUsername(appUser.getUsername())
                .isPresent();


        String token = UUID.randomUUID().toString();

        if(userEmailExists || userUsernameExists) {

            AppUser existingUser = appUserRepository.findByEmail(appUser.getEmail()).get();

            if(existingUser.isEnabled()){
                   throw new AppUserException("Username or Email already taken");
            }


            //Deletes old token if a new token is requested
            confirmationTokenService.deleteConfirmationTokenByAppUserId(existingUser.getAppuser_id());

            AppUser existingAppUser = appUserRepository.findByUsername(appUser.getUsername()).get();

            //Sets new confirmation token for existing AppUser
            setConfirmationToken(token, existingAppUser);

            return "resending confirmation Email with new token: " + token;
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        //Sets role for AppUser
        setAppUserRole(appUser);

        if(!EmailConfiguration.EMAIL_CONFIRMATION_REQUIRED){
            appUser.setEnabled(true);
        }

        //Saves AppUser in database
        appUserRepository.save(appUser);

        //Sets confirmation token for AppUser
        setConfirmationToken(token, appUser);

        return token;
    }

    //Enables AppUser
    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    public void setAppUserRole(AppUser appUser){
        if(appUser.getRole().toString() == "DOG_TRAINER"){
            appUser.setRole(AppUserRoles.DOG_TRAINER);
        }
        if(appUser.getRole().toString() == "DOG_OWNER"){
            appUser.setRole(AppUserRoles.DOG_OWNER);
        }
    }

    public void setConfirmationToken(String token, AppUser appUser){

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);


        String link = EmailConfiguration.EMAIL_CONFIRMATION_LINK + token;
        emailSender.send(
                appUser.getEmail(),
                buildEmail(appUser.getUsername(), link));

    }

    @Transactional
    public AppUser addAppUserImage(AppUser appUser, MultipartFile multipartFile) throws IOException {

        ImageHandler userImageHandler = new ImageHandler();

        UserImages appUserImages = userImageHandler.getAppUserImageFromMultipartfile(multipartFile);

        if(!(appUser.getUserImages() == null)){
            appUserImageRepository.delete(appUser.getUserImages());
        }

        appUserImages = appUserImageRepository.save(appUserImages);
        appUser.setUserImages(appUserImages);

        return appUserRepository.save(appUser);
    }

    public AppUser addNewTraining(AppUser appUser, Training training){

        training.addAppUser(appUser);
        trainingRepository.save(training);
        appUser.addTraining(training);

        return appUserRepository.save(appUser);
    }

    public AppUser addNewLocation(AppUser appUser, Location location){

        locationRepository.save(location);
        appUser.setLocation(location);

        return appUserRepository.save(appUser);
    }

    public List<AppUser> getAppUsersInRange(BigDecimal Lat, BigDecimal Lng, Double km){

        List<AppUser> appUsers = appUserRepository.findAll();

        double N = Lat.doubleValue();
        double E = Lng.doubleValue();

        BigDecimal latRange = locationService.getLatRange(Lat, km).abs();
        BigDecimal lngRange = locationService.getLngRange(Lng, km).abs();

        List<AppUser> appUserInRange = new ArrayList<>();

        for (AppUser a:appUsers) {
            Location location = a.getLocation();


            if (location != null) {
                double tN = location.getN().doubleValue();
                double tE = location.getE().doubleValue();

                // Check if the training's N and E values are within the range of the given location
                if (tN >= (N - latRange.doubleValue()) && tN <= (N + latRange.doubleValue()) &&
                        tE >= (E - lngRange.doubleValue()) && tE <= (E + lngRange.doubleValue())) {
                    appUserInRange.add(a);
                }
            }
        }

        return appUserInRange;
    }






    //Builds the email that gets sent for registration
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
