package at.happydog.test.preload;

import at.happydog.test.Handler.ImageLoader;
import at.happydog.test.enity.AppUserRating;
import at.happydog.test.enity.Location;
import at.happydog.test.enity.Training;
import at.happydog.test.enity.UserImages;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RatingFactory {


    public List<AppUserRating> createRating(int number) {
        List<AppUserRating> ratingList = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            switch (i % 3) {

                case 0:

                    AppUserRating rating1 = new AppUserRating(4, "Positiv, gute Trainer, Verbesserung im Gehorsam, Gruppentraining strukturiert. Wenig individuelle Aufmerksamkeit");

                    ratingList.add(rating1);
                    continue;

                case 1:
                    AppUserRating rating2 = new AppUserRating(5, "Empfehlenswertes Hundetraining! Kompetente Trainer, individuelles Training, schnelle Fortschritte. Danke!");
                    ratingList.add(rating2);
                    continue;

                case 2:
                   AppUserRating rating3 = new AppUserRating(1, "Enttäuschendes Hundetraining, unprofessionelle Trainer, keine Fortschritte, keine individuelle Betreuung. Nicht empfehlenswert.");
                   ratingList.add(rating3);

            }

        }
        return ratingList;
    }
}
