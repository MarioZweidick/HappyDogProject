package at.happydog.test.controller;

import at.happydog.test.api.google.geocoding.Geocoding;
import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.AppUserRoles;
import at.happydog.test.enity.Location;
import at.happydog.test.enity.Training;
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
    private final LocationService locationService;


    //Misc

    @RequestMapping("/login")
    public String loginPage(){
        return "entry/login.html";
    }

    @RequestMapping("/logout-success")
    public String logoutPage(){
        return "entry/logout.html";
    }

    @RequestMapping("/successful-login")
    public String onLoginSuccess(){
        return "redirect:/user/profile";
    }

    @RequestMapping("/email-confirmation")
    public String emailConfirmationPage(){
        return "entry/email-confirmation.html";
    }


    //Profile

    @GetMapping("/profile")
    public ModelAndView userProfileView(){
        ModelAndView owner = new ModelAndView("profile/owner");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(auth.getName());

        if(appUser.getRole() == AppUserRoles.DOG_TRAINER){
            ModelAndView trainer = new ModelAndView("profile/trainer");
            trainer.addObject("appuser", appUser);
            trainer.addObject("trainings", trainingService.getTrainingListForAppUser(appUser.getAppuser_id()));
            return trainer;
        }
        owner.addObject("appuser", appUser);
        return owner;
    }

    @GetMapping("/profile/image/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id) {
        Optional<AppUser> optionalAppUser = Optional.ofNullable(appUserService.findAppUserById(id));

        if(optionalAppUser.isPresent()){
            byte[] image = appUserService.downloadImageFromAppUser(optionalAppUser.get());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/jpeg")).body(image);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.valueOf("image/jpeg")).body(null);
        }
    }

    @PostMapping("/profile/save-image")
    public String saveAppUserImage(@ModelAttribute AppUser appUser, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        appUser = (AppUser) appUserService.loadUserByUsername(auth.getName());

        appUserService.addAppUserImage(appUser, multipartFile);
        return "redirect:/user/profile";
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
                               @RequestParam(value = "plz") String plz) throws IOException {

        return trainingService.saveTraining(title, description, price, date, beginn, end, street, streetNumber, city, plz);
    }

    @PostMapping("/profile/save-location")
    public String saveLocation(@RequestParam(value = "street") String street,
                               @RequestParam(value = "streetNumber") String streetNumber,
                               @RequestParam(value = "city") String city,
                               @RequestParam(value = "plz") String plz) throws IOException {

        return trainingService.saveLocation(street,streetNumber,city,plz);
    }
}
