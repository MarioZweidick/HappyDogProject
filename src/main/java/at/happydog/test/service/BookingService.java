package at.happydog.test.service;

import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.AppUserRoles;
import at.happydog.test.enity.Booking;
import at.happydog.test.enity.Training;
import at.happydog.test.exception.custom.BookingException;
import at.happydog.test.exception.custom.TrainingException;
import at.happydog.test.repository.AppUserRepository;
import at.happydog.test.repository.BookingRepository;
import at.happydog.test.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TrainingRepository trainingRepository;

    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;


    public Booking bookTraining(Training training, Long buyer_id, AppUser offerer){

        AppUser buyer = appUserService.findAppUserById(buyer_id);

        if(buyer.getRole().equals(AppUserRoles.DOG_OWNER) && !training.getIsBooked()){
            training.setVisible(false);
            training.setIsBooked(true);
            training.setBuyer_id(buyer_id);


            appUserService.addNewTraining(buyer, training);

            return bookingRepository.save(new Booking(offerer.getAppuser_id(), buyer_id, training));
        }else
            throw new BookingException("Training wurde bereits gebucht!");

    }



    public void cancelTraining(Training training, Long buyer_id, AppUser offerer){

        AppUser buyer = appUserService.findAppUserById(buyer_id);

        if(buyer.getRole().equals(AppUserRoles.DOG_OWNER) && training.getIsBooked() && buyer.getTrainings().size() > 0){
            training.setVisible(true);
            training.setIsBooked(false);
            training.setBuyer_id(null);

            buyer.deleteTraining(training);
            Optional<Booking> bookingOptional = bookingRepository.findByTrainingId(training.getTraining_id());

            if(bookingOptional.isPresent()){
                bookingRepository.delete(bookingOptional.get());
            }

        }else{
            throw new BookingException("Training wurde bereits storniert!");
        }
    }
}
