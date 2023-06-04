package at.happydog.test.controller;

import at.happydog.test.Handler.ImageHandler;
import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.UserImages;
import at.happydog.test.service.AppUserService;
import at.happydog.test.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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


        String returnRedirectString;

        if(location.isEmpty())
            return "redirect:/index?q=error_no_location";

        try {
            returnRedirectString = locationService.createRedirectString(location, range);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(returnRedirectString == null)
        {
            return "redirect:/index?q=error_location";
        }

        return returnRedirectString;

    }


    @GetMapping()
    public ModelAndView ShowAppUsers(@RequestParam("lat") BigDecimal lat,
                                      @RequestParam("lng") BigDecimal lng,
                                      @RequestParam("range") Integer range) throws IOException {

        ModelAndView mav = new ModelAndView("search");
        List<AppUser> appUsers = appUserService.getAppUsersInRange(lat, lng, range.doubleValue());
        /*if(mav.isEmpty())
        {
            AppUser noTrainer = new AppUser();
            noTrainer.setFirstname("Kein Hundetrainer gefunden");
            noTrainer.setLastname("");
            UserImages noTrainerImage = new UserImages();
            ImageHandler imageHandler = new ImageHandler();
            Path path = Paths.get("static/src/img/shutterstock_69823651-1000x608.jpg");
            MultipartFile multipartFile = new MockMultipartFile("SadDog.jpg","shutterstock_149148035.jpg","image/jpg", Files.readAllBytes(path));
            noTrainerImage.setImageData(imageHandler.getAppUserImageFromMultipartfile(multipartFile).getImageData());

            mav.addObject(noTrainer);
        }else {}*/

        mav.addObject("appusers", appUsers);
        return mav;
    }
}
