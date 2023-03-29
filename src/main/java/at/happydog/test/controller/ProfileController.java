package at.happydog.test.controller;

import at.happydog.test.api.google.geocoding.Geocoding;
import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.AppUserRoles;
import at.happydog.test.enity.Location;
import at.happydog.test.enity.Training;
import at.happydog.test.service.AppUserService;
import at.happydog.test.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
 ProfileController class

 This is the controller for the profile page.

 This class is mapping: '/user/profile' , '/user/profile/image/{id}' , '/user/profile/save-image' , '/user/profile/save-training'
 **/

@org.springframework.stereotype.Controller
@RequestMapping(path = "/user/profile")
public class ProfileController {

    private final AppUserService appUserService;
    private final TrainingService trainingService;

    @Autowired
    public ProfileController(AppUserService appUserService, TrainingService trainingService) {
        this.appUserService = appUserService;
        this.trainingService = trainingService;
    }

    @GetMapping
    public ModelAndView userProfileView(){

        ModelAndView owner = new ModelAndView("tprofile/owner");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        AppUser appUser = (AppUser) appUserService.loadUserByUsername(auth.getName());

        if(appUser.getRole() == AppUserRoles.DOG_TRAINER){
            ModelAndView trainer = new ModelAndView("tprofile/trainer");
            trainer.addObject("appuser", appUser);
            trainer.addObject("trainings", trainingService.getTrainingListForAppUser(appUser.getAppuser_id()));
            return trainer;
        }

        owner.addObject("appuser", appUser);
        return owner;
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id) {
        Optional<AppUser> optionalAppUser = Optional.ofNullable(appUserService.findAppUserById(id));

        if(optionalAppUser.isPresent()){
            byte[] image = appUserService.downloadImageFromAppUser(optionalAppUser.get());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/jpeg")).body(image);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.valueOf("image/jpeg")).body(null);
        }
    }

    @PostMapping("/save-image")
    public String saveAppUserImage(@ModelAttribute AppUser appUser, @RequestParam("image") MultipartFile multipartFile) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        appUser = (AppUser) appUserService.loadUserByUsername(auth.getName());

        appUserService.addAppUserImage(appUser, multipartFile);
        return "redirect:/user/profile";
    }

    @PostMapping("/save-training")
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


        List<BigDecimal> cords= new Geocoding().geocode(street +","+streetNumber+","+plz+","+city);

        Location newLocation = new Location(street, streetNumber, city, plz, cords.get(0), cords.get(1));
        Training newTraining = new Training(title, description, price, date, beginn, end, newLocation);


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(auth.getName());

        appUserService.addNewTraining(appUser, newTraining);
        return "redirect:/user/profile";
    }

}
