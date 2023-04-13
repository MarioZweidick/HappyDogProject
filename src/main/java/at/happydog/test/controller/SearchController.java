package at.happydog.test.controller;

import at.happydog.test.enity.AppUser;
import at.happydog.test.service.AppUserService;
import at.happydog.test.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {

    private final LocationService locationService;
    private final AppUserService appUserService;


    @PostMapping()
    public String SearchLocation(@RequestParam("location") String location,
                                       @RequestParam("range") Integer range) throws IOException {
        return locationService.createRedirectString(location, range);
    }


    @GetMapping()
    public ModelAndView ShowAppUsers(@RequestParam("lat") BigDecimal lat,
                                      @RequestParam("lng") BigDecimal lng,
                                      @RequestParam("range") Integer range) throws IOException {

        ModelAndView mav = new ModelAndView("search");
        List<AppUser> appUsers = appUserService.getAppUsersInRange(lat, lng, range.doubleValue());
        mav.addObject("appusers", appUsers);

        return mav;
    }
}
