package at.happydog.test.controller;

import at.happydog.test.service.AppUserService;
import at.happydog.test.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**

 Main Controller mapped Requests an /* zur richtigen HTML Page

 z.b. mainPage mapped Get Requests von localhost:8080/ an index.html

 **/

@org.springframework.stereotype.Controller
public class Controller {


    private final TrainingService trainingService;
    private final AppUserService appUserService;

    @Autowired
    public Controller(TrainingService trainingService, AppUserService appUserService) {
        this.trainingService = trainingService;
        this.appUserService = appUserService;
    }

    @RequestMapping(value={"/", "/index", "/home"})
    public ModelAndView trainingList(){
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("trainings", trainingService.getTrainingList());
        return mav;
    }

    @RequestMapping("/user/login")
    public String loginPage(){
        return "login.html";
    }

    @RequestMapping("/user/logout-success")
    public String logoutPage(){
        return "logout.html";
    }

    @RequestMapping("/successful-login")
    public String onLoginSuccess(){
        return "redirect:/user/profile";
    }

    @RequestMapping("/email-confirmation")
    public String emailConfirmationPage(){
        return "email-confirmation.html";
    }

}
