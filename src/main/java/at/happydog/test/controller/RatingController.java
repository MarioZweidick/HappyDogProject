package at.happydog.test.controller;

import at.happydog.test.enity.AppUser;
import at.happydog.test.exception.custom.AppUserException;
import at.happydog.test.exception.custom.RatingException;
import at.happydog.test.service.AppUserRatingService;
import at.happydog.test.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RatingController {

    private final AppUserRatingService appUserRatingService;
    private final AppUserService appUserService;

    @Autowired
    public RatingController(AppUserRatingService appUserRatingService, AppUserService appUserService) {
        this.appUserRatingService = appUserRatingService;
        this.appUserService = appUserService;
    }



    @PostMapping("/save-rating")
    public ResponseEntity<String> saveRating(@RequestParam("trainer-id") Long id,
                                             @RequestParam("rating") Integer rating,
                                             @RequestParam("comment") String comment){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser trainer = (AppUser) appUserService.loadUserByUsername(auth.getName());

        try {
            AppUser user = appUserService.findAppUserById(id);
            appUserRatingService.saveRating(rating, comment, user, trainer);
        } catch (RatingException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorMessage());
        }catch (AppUserException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Bewertung wurde hinzugefügt!");
    }
}
