package at.happydog.test.service;

import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.Training;
import at.happydog.test.repository.AppUserRepository;
import at.happydog.test.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    public TrainingService(TrainingRepository trainingRepository, AppUserRepository appUserRepository) {
        this.trainingRepository = trainingRepository;
        this.appUserRepository = appUserRepository;
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


}
