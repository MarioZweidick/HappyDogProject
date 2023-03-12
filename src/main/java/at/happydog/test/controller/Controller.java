package at.happydog.test.controller;

import at.happydog.test.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**

 Main Controller mapped Requests an /* zur richtigen HTML Page

 z.b. mainPage mapped Get Requests von localhost:8080/ an index.html

 **/

@org.springframework.stereotype.Controller
public class Controller {

    private final AppUserService appUserService;

    @Autowired
    public Controller(AppUserService appUserService) {
        this.appUserService = appUserService;
    }


    @RequestMapping(value="/")
    public String mainPage(){
        return "index.html";
    }

    @RequestMapping("/user/login")
    public String loginPage(){
        return "login.html";
    }

    @RequestMapping("/user/logout-success")
    public String logoutPage(){
        return "logout.html";
    }

    @RequestMapping("/email-confirmation")
    public String emailConfirmationPage(){
        return "email-confirmation.html";
    }


}
