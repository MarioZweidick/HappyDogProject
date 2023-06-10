package at.happydog.test.service;

import at.happydog.test.Handler.ImageHandler;
import at.happydog.test.api.google.geocoding.Geocoding;
import at.happydog.test.enity.*;
import at.happydog.test.exception.custom.AppUserException;
import at.happydog.test.exception.custom.TrainingException;
import at.happydog.test.repository.TrainingRepository;
import at.happydog.test.repository.TrainingsImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    private final TrainingsImageRepository trainingsImageRepository;




    public Optional<Training> getTrainingById(Long id){
        Optional<Training> training = trainingRepository.findById(id);

        return training;
    }

    public List<Training> getTrainingListForAppUser(Long id, Boolean visible){
        Optional<AppUser> appUser = Optional.ofNullable(appUserService.findAppUserById(id));

        List<Training> trainingList = new ArrayList<>();


        if(appUser.isPresent() && !appUser.get().getTrainings().isEmpty()){

            if(appUser.get().getRole() == AppUserRoles.DOG_TRAINER){
                return appUser.get().getTrainings();
            }

            if(appUser.get().getRole() == AppUserRoles.DOG_OWNER && !visible){
                for (Training tr : appUser.get().getTrainings()) {
                    if (!tr.getVisible()) {
                        trainingList.add(tr);
                    }
                }
                return trainingList;
            }

            if(visible){
                for (Training tr:appUser.get().getTrainings()) {
                    if(tr.getVisible()){
                        trainingList.add(tr);
                    }
                }
                return trainingList;
            }

        }
        return null;
    }


    public void deleteTraining(Training training, Long userid){

        AppUser user = appUserService.findAppUserById(userid);

        if(user.getRole().equals(AppUserRoles.DOG_TRAINER) && !training.getIsBooked() && user.getTrainings().size() > 0){

            user.deleteTraining(training);
            trainingRepository.delete(training);

        }else{
            throw new TrainingException("Training konnte nicht gelöscht werden!");
        }
    }


    public String saveTraining(String title, String description, Double price, Date date, LocalTime beginn, LocalTime end, String street, String streetNumber, String city, String plz, MultipartFile picture) throws IOException, InterruptedException {
        String geoLocation = (street + "," + streetNumber + "," + plz + "," + city).replace(".", "-").toLowerCase();

        List<String> cords = geocoding.geocode(geoLocation);

        if(cords ==  null)
            return "redirect:/user/profile?q=error_training";

        BigDecimal N = new BigDecimal(cords.get(4));
        BigDecimal E = new BigDecimal(cords.get(5));

        Location newLocation = new Location(street, streetNumber, city, plz, N, E);

        if (locationService.findLocation(N, E)) {
            locationService.save(newLocation);
        }

        Training newTraining = new Training(title, description, price, date, beginn, end, newLocation, true);

        addTrainingImage(newTraining, picture);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(auth.getName());

        appUserService.addNewTraining(appUser, newTraining);
        return "redirect:/user/profile";
    }

    public String saveLocation(String street, String streetNumber, String city, String plz) throws IOException, InterruptedException {
        String geoLocation = (street + "," + streetNumber + "," + plz + "," + city).replace(".", "-").toLowerCase();

        List<String> cords = geocoding.geocode(geoLocation);
        if(cords==null)
            return "redirect:/user/profile?q=error_location";

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

    @Transactional
    public Training addTrainingImage(Training training, MultipartFile multipartFile) throws IOException {

        ImageHandler trainingsImageHandler = new ImageHandler();

        UserImages trainingsImage = trainingsImageHandler.getAppUserImageFromMultipartfile(multipartFile);

        if(!(training.getTrainingsImage()== null)){
            trainingsImageRepository.delete(training.getTrainingsImage());
        }

        trainingsImage = trainingsImageRepository.save(trainingsImage);
        training.setTrainingsImage(trainingsImage);

        return trainingRepository.save(training);
    }

    public Training findTrainingById(Long id) throws Exception {
        if(trainingRepository.findById(id).isPresent())
            return trainingRepository.findById(id).get();
        else
            throw new AppUserException("Fehler: Benutzer wurde nicht gefunden!");

    }

}
