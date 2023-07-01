package at.happydog.test.controller;

import at.happydog.test.exception.custom.AppUserException;
import at.happydog.test.registrationUtil.UserRegistrationRequest;
import at.happydog.test.service.UserRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/user/registration")
@AllArgsConstructor
public class UserRegistrationController {
    private final UserRegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationRequest request){

        try {
            registrationService.register(request);
        } catch (AppUserException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Benutzer erfolgreich registriert!");
    }

    @GetMapping()
    public ModelAndView registrationForm(){
        ModelAndView mav = new ModelAndView("entry/registration");
        return mav;
    }

    @GetMapping(path = "/confirm")
    public ModelAndView confirm(@RequestParam("token") String token) throws IOException {
        ModelAndView mav = new ModelAndView("entry/email-confirmation");
        registrationService.confirmToken(token);
        return mav;
    }
}
