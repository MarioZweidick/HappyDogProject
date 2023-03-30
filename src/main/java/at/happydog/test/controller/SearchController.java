package at.happydog.test.controller;

import at.happydog.test.api.google.geocoding.Geocoding;
import at.happydog.test.enity.Location;
import at.happydog.test.enity.Training;
import at.happydog.test.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class SearchController {

    private final LocationService locationService;


    @Autowired
    public SearchController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/search")
    public String SearchLocation(@RequestParam("location") String location,
                                       @RequestParam("range") Integer range) throws IOException {

        String newloc = location.replaceAll("\\s+",",").replace(".", "-").toLowerCase();


        Location existingLocation = locationService.inputMatcher(location);

        if(existingLocation == null){
            List<String> geolocation = new Geocoding().geocode(newloc);

            //String street, String streetNumber, String city, String plz, BigDecimal n, BigDecimal e
            Location newLocation = new Location(geolocation.get(0), geolocation.get(1), geolocation.get(2), geolocation.get(3), new BigDecimal(geolocation.get(4)), new BigDecimal(geolocation.get(5)));
            locationService.save(newLocation);

            if(geolocation.get(2).isEmpty()){
                return "redirect:/search?error=unspecific-location";
            }

            return "redirect:/search?lat="+geolocation.get(4)+"&lng="+geolocation.get(5)+"&range="+range;
        }else{
            return "redirect:/search?lat="+existingLocation.getN()+"&lng="+existingLocation.getE()+"&range="+range;
        }

    }


    @GetMapping("/search")
    public ModelAndView ShowTrainings(@RequestParam("lat") BigDecimal lat,
                                      @RequestParam("lng") BigDecimal lng,
                                      @RequestParam("range") Integer range) throws IOException {

        ModelAndView mav = new ModelAndView("search");

        List<Training> trainings = locationService.getTrainingsInRange(lat, lng, range.doubleValue());

        mav.addObject("trainings", trainings);

        return mav;
    }
}
