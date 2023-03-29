package at.happydog.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 UserController class

 This is the controller for the user login handling.

 This class is mapping: '/user/login' , '/user/logout-success' , '/successful-login' , '/email-confirmation'
 **/

@Controller
public class UserController {

    @RequestMapping("/user/login")
    public String loginPage(){
        return "tentry/login.html";
    }

    @RequestMapping("/user/logout-success")
    public String logoutPage(){
        return "tentry/logout.html";
    }

    @RequestMapping("/successful-login")
    public String onLoginSuccess(){
        return "redirect:/user/profile";
    }

    @RequestMapping("/email-confirmation")
    public String emailConfirmationPage(){
        return "tentry/email-confirmation.html";
    }
}
