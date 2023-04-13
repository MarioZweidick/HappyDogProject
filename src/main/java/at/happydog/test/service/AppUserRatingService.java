package at.happydog.test.service;

import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.AppUserRating;
import at.happydog.test.exception.custom.RatingException;
import at.happydog.test.repository.AppUserRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserRatingService {

    private final AppUserRatingRepository appUserRatingRepository;

    @Autowired
    public AppUserRatingService(AppUserRatingRepository appUserRatingRepository) {
        this.appUserRatingRepository = appUserRatingRepository;
    }


    public AppUserRating saveRating(Integer rating, String comment, AppUser fromAppUser, AppUser toAppUser) throws RatingException{

        if(rating <= 5 && rating >= 1) {
            return appUserRatingRepository.save(new AppUserRating(rating, comment, fromAppUser, toAppUser));
        }else{
            throw new RatingException("Rating muss zwischen 0 und 6 liegen!");
        }

    }

}
