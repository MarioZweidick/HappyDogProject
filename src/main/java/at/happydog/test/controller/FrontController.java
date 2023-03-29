package at.happydog.test.controller;

import at.happydog.test.service.AppUserService;
import at.happydog.test.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 FrontController class

 This is the controller for the frontpage.

 This class is mapping: '/' , '/index' , '/list' , 'home'
 **/

@org.springframework.stereotype.Controller
public class FrontController {

    private final TrainingService trainingService;
    private final AppUserService appUserService;

    @Autowired
    public FrontController(TrainingService trainingService, AppUserService appUserService) {
        this.trainingService = trainingService;
        this.appUserService = appUserService;
    }

    @RequestMapping(value={"/", "/index", "/home"})
    public ModelAndView trainingList(){
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("trainings", trainingService.getTrainingList());
        return mav;
    }



}
