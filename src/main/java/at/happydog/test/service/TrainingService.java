package at.happydog.test.service;

import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.Training;
import at.happydog.test.repository.AppUserRepository;
import at.happydog.test.repository.LocationRepository;
import at.happydog.test.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 TrainingService class

 This class handles business logic for the Trainings and receives data through the repositories
 **/

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final AppUserRepository appUserRepository;

    private final LocationRepository locationRepository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository, AppUserRepository appUserRepository, LocationRepository locationRepository) {
        this.trainingRepository = trainingRepository;
        this.appUserRepository = appUserRepository;
        this.locationRepository = locationRepository;
    }

    public Optional<Training> getTrainingById(Long id){
        Optional<Training> training = trainingRepository.findById(id);

        return training;
    }


    public List<Training> getTrainingList(){return trainingRepository.findAll();}

    public List<Training> getTrainingListForAppUser(Long id){
        Optional<AppUser> appUser = appUserRepository.findById(id);

        if(appUser.isPresent() && !appUser.get().getTrainings().isEmpty()){
            return appUser.get().getTrainings();
        }
        return null;
    }

    public List<Training> getTrainingListByLocation(String location){

        List<Training> trainings = trainingRepository.findAll();
        List<Training> trainingsByLocation = new ArrayList<>();

        for (Training t:trainings) {
            if( t.getLocation().getCity().toLowerCase() == location){
                trainingsByLocation.add(t);
            }
        }

        return trainingsByLocation;
    }


}
