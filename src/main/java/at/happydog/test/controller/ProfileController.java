package at.happydog.test.controller;

import at.happydog.test.enity.AppUser;
import at.happydog.test.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**

 Profile Controller mapped Requests an /user/profile zur richtigen HTML Page

 Kann in zukunft in den Main Controller eingefügt werden. Arbeitet mit MVC - Model View Controller
 damit den eingeloggte (Authentifizierte) Benutzer an das Frontend übergeben werden kann

 **/

@org.springframework.stereotype.Controller
@RequestMapping(path = "/user/profile")
public class ProfileController {

    private final AppUserService appUserService;

    @Autowired
    public ProfileController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public ModelAndView userProfileView(){
        //Mapped die View auf profile.html
        ModelAndView mav = new ModelAndView("profile.html");

        //Holt den Context (eingeloggten User) aus dem SecurityContextHolder
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Erstellt einen temporären AppUser für den eingeloggten User
        AppUser appUser = appUserService.findAppUserByUsername(auth.getName());

        //Added den AppUser zur Model View um in durch die Template Engine (Thymleaf) aufzurufen
        mav.addObject("appuser", appUser);

        return mav;
    }

}
