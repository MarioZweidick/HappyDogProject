package at.happydog.test.service;

import at.happydog.test.api.google.geocoding.Geocoding;
import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.Location;
import at.happydog.test.enity.Training;
import at.happydog.test.repository.AppUserRepository;
import at.happydog.test.repository.LocationRepository;
import at.happydog.test.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 TrainingService class

 This class handles business logic for the Trainings and receives data through the repositories
 **/

@Service
@AllArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final AppUserService appUserService;
    private final LocationService locationService;
    private final Geocoding geocoding;


    public Optional<Training> getTrainingById(Long id){
        Optional<Training> training = trainingRepository.findById(id);

        return training;
    }

    public List<Training> getTrainingListForAppUser(Long id){
        Optional<AppUser> appUser = Optional.ofNullable(appUserService.findAppUserById(id));

        if(appUser.isPresent() && !appUser.get().getTrainings().isEmpty()){
            return appUser.get().getTrainings();
        }
        return null;
    }

    public String saveTraining(String title, String description, Double price, Date date, LocalTime beginn, LocalTime end, String street, String streetNumber, String city, String plz) throws IOException {
        String geoLocation = (street + "," + streetNumber + "," + plz + "," + city).replace(".", "-").toLowerCase();

        List<String> cords = geocoding.geocode(geoLocation);

        BigDecimal N = new BigDecimal(cords.get(4));
        BigDecimal E = new BigDecimal(cords.get(5));

        Location newLocation = new Location(street, streetNumber, city, plz, N, E);

        if (locationService.findLocation(N, E)) {
            locationService.save(newLocation);
        }

        Training newTraining = new Training(title, description, price, date, beginn, end, newLocation);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(auth.getName());

        appUserService.addNewTraining(appUser, newTraining);
        return "redirect:/user/profile";
    }

    public String saveLocation(String street, String streetNumber, String city, String plz) throws IOException {
        String geoLocation = (street + "," + streetNumber + "," + plz + "," + city).replace(".", "-").toLowerCase();

        List<String> cords = geocoding.geocode(geoLocation);

        BigDecimal N = new BigDecimal(cords.get(4));
        BigDecimal E = new BigDecimal(cords.get(5));

        Location newLocation = new Location(street, streetNumber, city, plz, N, E);

        if (locationService.findLocation(N, E)) {
            locationService.save(newLocation);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(auth.getName());

        appUserService.addNewLocation(appUser, newLocation);
        return "redirect:/user/profile";
    }


}
