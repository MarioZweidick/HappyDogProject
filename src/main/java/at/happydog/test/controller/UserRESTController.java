package at.happydog.test.controller;

import at.happydog.test.registrationUtil.UserRegistrationRequest;
import at.happydog.test.service.UserRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 UserRESTController class

 This is the REST controller for the user registration handling.

 This class is mapping: '/user/registration' , '/user/registration/confirm'
 **/

@RestController
@RequestMapping(path = "/user/registration")
@AllArgsConstructor
public class UserRESTController {

    private final UserRegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody UserRegistrationRequest request){

        return registrationService.register(request);
    }

    @GetMapping
    public ModelAndView registrationForm(){
        ModelAndView mav = new ModelAndView("tentry/registration");
        return mav;
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token, HttpServletResponse response, HttpServletRequest request) throws IOException {

        registrationService.confirmToken(token);

        response.sendRedirect(request.getContextPath() + "/email-confirmation");
        return "";
    }

}
