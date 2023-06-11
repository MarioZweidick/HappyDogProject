package at.happydog.test.controller;

import at.happydog.test.Handler.ImageHandler;
import at.happydog.test.api.google.geocoding.Geocoding;
import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.AppUserRoles;
import at.happydog.test.enity.Location;
import at.happydog.test.enity.Training;
import at.happydog.test.exception.custom.BookingException;
import at.happydog.test.exception.custom.TrainingException;
import at.happydog.test.registrationUtil.UserRegistrationRequest;
import at.happydog.test.service.AppUserService;
import at.happydog.test.service.LocationService;
import at.happydog.test.service.TrainingService;
import at.happydog.test.service.UserRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 UserProfileController class

 This is the controller for the user login handling.

 This class is mapping: '/user/login' , '/user/logout-success' , '/successful-login' , '/email-confirmation'
 **/

@Controller
@RequestMapping(path = "/user")
@AllArgsConstructor
public class UserProfileController {

    private final AppUserService appUserService;
    private final TrainingService trainingService;


    @RequestMapping("/login")
    public String loginPage(){
        return "entry/login.html";
    }

    @RequestMapping("/logout-success")
    public String logoutPage(){
        return "entry/logout.html";
    }

    @RequestMapping("/email-confirmation")
    public String emailConfirmationPage(){
        return "entry/email-confirmation.html";
    }


    //Profile

    @GetMapping("/profile")
    public ModelAndView userProfileView(){
        ModelAndView owner = new ModelAndView("profile/owner");
        ModelAndView trainer = new ModelAndView("profile/trainer");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(auth.getName());

        if(appUser.getRole() == AppUserRoles.DOG_TRAINER){

            trainer.addObject("appuser", appUser);
            trainer.addObject("trainings", trainingService.getTrainingListForAppUser(appUser.getAppuser_id(), null));
            return trainer;
        }else{
            owner.addObject("appuser", appUser);
            owner.addObject("trainings", trainingService.getTrainingListForAppUser(appUser.getAppuser_id(), false));
            return owner;
        }

    }

    @GetMapping("/profile-edit")
    public ModelAndView userProfileEditView(){
        ModelAndView owner = new ModelAndView("profile/edit-owner");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(auth.getName());

        if(appUser.getRole() == AppUserRoles.DOG_TRAINER){
            ModelAndView trainer = new ModelAndView("profile/edit-trainer");
            trainer.addObject("appuser", appUser);
            return trainer;
        }
        owner.addObject("appuser", appUser);

        return owner;
    }

    @GetMapping( "/delete-training")
    public String deleteTraining(@RequestParam Long training_id){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) appUserService.loadUserByUsername(auth.getName());

        Training training = trainingService.getTrainingById(training_id).get();

        try {
            trainingService.deleteTraining(training, user.getAppuser_id());
        }catch (TrainingException tex){
            System.out.println(tex.getErrorMessage());
            return "redirect:/user/profile";
        }

        return "redirect:/user/profile";
    }

    @GetMapping("/profile/image/{id}")
    public ResponseEntity<byte[]> downloadUserImage(@PathVariable Long id) {
        Optional<AppUser> optionalAppUser = Optional.ofNullable(appUserService.findAppUserById(id));
        ImageHandler imageHandler = new ImageHandler();

        if(optionalAppUser.isPresent()){
            byte[] image = imageHandler.downloadImageFromAppUser(optionalAppUser.get());

                if(optionalAppUser.get().getUserImages() != null){
                    String type = optionalAppUser.get().getUserImages().getType();
                    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(type)).body(image);
                }else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    }

    @GetMapping("/training/image/{id}")
    public ResponseEntity<byte[]> downloadTrainingsImage(@PathVariable Long id) throws Exception {
        Optional<Training> optionalTraining = Optional.ofNullable(trainingService.findTrainingById(id));
        ImageHandler imageHandler = new ImageHandler();

        if(optionalTraining.isPresent()){
            byte[] image = imageHandler.downloadImageFromTraining(optionalTraining.get());

            if(optionalTraining.get().getTrainingsImage() != null){
                String type = optionalTraining.get().getTrainingsImage().getType();
                return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(type)).body(image);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/profile/save-image")
    public String saveAppUserImage(@ModelAttribute AppUser appUser, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        appUser = (AppUser) appUserService.loadUserByUsername(auth.getName());

        appUserService.addAppUserImage(appUser, multipartFile);

        return "redirect:/user/profile-edit";
    }

    @PostMapping("/profile/save-training")
    public String saveTraining(@RequestParam("title") String title,
                               @RequestParam("description") String description,
                               @RequestParam("price") Double price,
                               @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                               @RequestParam("beginntime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalTime beginn,
                               @RequestParam("endtime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalTime end,
                               @RequestParam(value = "street") String street,
                               @RequestParam(value = "streetNumber") String streetNumber,
                               @RequestParam(value = "city") String city,
                               @RequestParam(value = "plz") String plz ,
                               @RequestParam("picture") MultipartFile picture)
    {

        String returnString;
        try {
            returnString = trainingService.saveTraining(title, description, price, date, beginn, end, street, streetNumber, city, plz, picture);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(returnString.isEmpty())
            returnString = "Der Ort wurde nicht gefunden!";

        return returnString;

    }



    @PostMapping("/profile/save-location")
    public String saveLocation(@RequestParam(value = "street") String street,
                               @RequestParam(value = "streetNumber") String streetNumber,
                               @RequestParam(value = "city") String city,
                               @RequestParam(value = "plz") String plz) throws IOException {


        String returnLocation;
        try {
            returnLocation = trainingService.saveLocation(street,streetNumber,city,plz);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(returnLocation.isEmpty())
            returnLocation = "Der Ort wurde nicht gefunden!";

        return returnLocation;
    }
}
