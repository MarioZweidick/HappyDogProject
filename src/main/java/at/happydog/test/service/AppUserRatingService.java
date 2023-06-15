package at.happydog.test.service;

import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.AppUserRating;
import at.happydog.test.exception.custom.RatingException;
import at.happydog.test.repository.AppUserRatingRepository;
import at.happydog.test.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserRatingService {

    private final AppUserRatingRepository appUserRatingRepository;
    private final AppUserRepository appUserRepository;


    @Autowired
    public AppUserRatingService(AppUserRatingRepository appUserRatingRepository, AppUserRepository appUserRepository) {
        this.appUserRatingRepository = appUserRatingRepository;
        this.appUserRepository = appUserRepository;
    }


    public AppUserRating saveRating(Integer rating, String comment, AppUser fromAppUser, AppUser toAppUser) throws RatingException{

        if(rating <= 5 && rating >= 1) {
            AppUser appUser = appUserRepository.getReferenceById(toAppUser.getAppuser_id());
            double averageRatings = averageRating(appUser.getRatings());
            appUser.setAverageRating(averageRatings);
            appUserRepository.save(appUser);

            return appUserRatingRepository.save(new AppUserRating(rating, comment, fromAppUser, toAppUser));
        }else{
            throw new RatingException("Rating muss zwischen 0 und 6 liegen!");
        }

    }


    public static double averageRating(List<AppUserRating> ratings)
    {
        double sumOfRatings = 0;
        double averageRatings = 0;
        for (AppUserRating appUserRating: ratings) {
            sumOfRatings+=appUserRating.getRating();
            averageRatings = sumOfRatings / ratings.size();
        }
       return averageRatings;
    }

}
