package at.happydog.test.controller;

import at.happydog.test.enity.AppUser;
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
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

/**

 Profile Controller mapped Requests an /user/profile zur richtigen HTML Page

 Kann in zukunft in den Main Controller eingefügt werden. Arbeitet mit MVC - Model View Controller
 damit den eingeloggte (Authentifizierte) Benutzer an das Frontend übergeben werden kann

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
        //Mapped die View auf profile.html
        ModelAndView mav = new ModelAndView("profile.html");

        //Holt den Context (eingeloggten User) aus dem SecurityContextHolder
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Erstellt einen temporären AppUser für den eingeloggten User
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(auth.getName());

        //Added den AppUser zur Model View um in durch die Template Engine (Thymleaf) aufzurufen
        mav.addObject("appuser", appUser);
        mav.addObject("trainings", trainingService.getTrainingListForAppUser(appUser.getAppuser_id()));

        return mav;
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
                               @RequestParam(value = "latitude", required = false) String latitude,
                               @RequestParam(value = "longitude", required = false) String longitude,
                               @RequestParam(value = "location-name", required = false) String locationName) throws IOException {



        Location newLocation = new Location(locationName, latitude, longitude);

        Training newTraining = new Training(title, description, price, date, beginn, end, newLocation);



        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(auth.getName());

        appUserService.addNewTraining(appUser, newTraining);
        return "redirect:/user/profile";
    }

}
