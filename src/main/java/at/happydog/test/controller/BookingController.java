package at.happydog.test.controller;

import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.Training;
import at.happydog.test.exception.custom.BookingException;
import at.happydog.test.service.AppUserService;
import at.happydog.test.service.BookingService;
import at.happydog.test.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class BookingController {

    private final TrainingService trainingService;
    private final AppUserService appUserService;
    private final BookingService bookingService;


    @GetMapping("/payment-form")
    public ModelAndView paymentPage(@RequestParam("training_id") Long training_id){
        ModelAndView mav = new ModelAndView("payment/payment-form");
        Training training = trainingService.getTrainingById(training_id).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(auth.getName());
        mav.addObject("training", training);
        mav.addObject("appuser", appUser);
        return mav;
    }

    @PostMapping("/payment-checkout")
    public ModelAndView checkoutPage(@RequestParam("training_id") Long training_id){
        ModelAndView mav = new ModelAndView("payment/payment-checkout");
        Training training = trainingService.getTrainingById(training_id).get();
        mav.addObject("training", training);
        return mav;
    }

    @GetMapping( "/book-training")
    public String bookTraining(@RequestParam Long training_id){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser buyer = (AppUser) appUserService.loadUserByUsername(auth.getName());

        Training training = trainingService.getTrainingById(training_id).get();

        try {
            bookingService.bookTraining(training, buyer.getAppuser_id(), training.getAppUsers().get(0));
        }catch (BookingException bex){
            System.out.println(bex.getErrorMessage());
            return "redirect:/order-confirmation-failure";
        }

        return "redirect:/order-confirmation-sucess";
    }

    @RequestMapping("/order-confirmation-sucess")
    public ModelAndView orderConfirmationSuccessPage(){
        return new ModelAndView("payment/order-confirmation-success");
    }

    @RequestMapping("/order-confirmation-failure")
    public ModelAndView orderConfirmationFailurePage(){
        return new ModelAndView("payment/order-confirmation-failure");
    }
}
