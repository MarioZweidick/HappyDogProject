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

 User Controller mapped Requests an /user/registration

 Dieser Controller handhabt alles was mit der User registration und der Email bestätigung zu tun hat

 Implementiert das UserRegistrationService

 **/

@RestController
@RequestMapping(path = "/user/registration")
@AllArgsConstructor
public class UserController {

    private final UserRegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody UserRegistrationRequest request){
        //returned die UserRegistrationService.register Methode und übergibt die UserRegistrationRequest
        //(username, firstname, lastname etc.
        return registrationService.register(request);
    }

    @GetMapping
    public ModelAndView registrationForm(){
        ModelAndView mav = new ModelAndView("registration.html");
        return mav;
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token, HttpServletResponse response, HttpServletRequest request) throws IOException {
        //Ruft die methode confirmToken auf und übergibt den Token zur Überprüfung an die Methode
        registrationService.confirmToken(token);

        //Nimmt den request context pfad (in dem fall localhost:8080) und redirected den User zur email-confirmation.html page
        response.sendRedirect(request.getContextPath() + "/email-confirmation");
        return "";
    }

}
