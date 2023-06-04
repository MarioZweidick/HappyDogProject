package at.happydog.test.controller;

import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.Training;
import at.happydog.test.exception.custom.AppUserException;
import at.happydog.test.registrationUtil.UserRegistrationRequest;
import at.happydog.test.service.AppUserRatingService;
import at.happydog.test.service.AppUserService;
import at.happydog.test.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ViewController {

    private final TrainingService trainingService;
    private final AppUserService appUserService;



    @RequestMapping(value={"/", "/index", "/home"})
    public ModelAndView indexView(){
        return new ModelAndView("index");
    }


    @GetMapping( "/rating/view")
    public ModelAndView ratingView(@RequestParam Long trainer_id){
        ModelAndView mav = new ModelAndView("profile/rating-view");
        AppUser appUser = appUserService.findAppUserById(trainer_id);
        mav.addObject("appuser", appUser);
        return mav;
    }

    @GetMapping( "/trainer/view")
    public ModelAndView trainerView(@RequestParam Long trainer_id){
        ModelAndView mav = new ModelAndView("profile/trainer-view");
        AppUser appUser = appUserService.findAppUserById(trainer_id);
        mav.addObject("appuser", appUser);
        return mav;
    }

    @GetMapping( "/training/view")
    public ModelAndView trainingView(@RequestParam Long training_id){
        ModelAndView mav = new ModelAndView("training");
        Training training = trainingService.getTrainingById(training_id).get();
        mav.addObject("training", training);
        return mav;
    }

}
