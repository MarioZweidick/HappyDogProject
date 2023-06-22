package at.happydog.test.preload;

import at.happydog.test.enity.*;
import at.happydog.test.repository.AppUserRepository;
import at.happydog.test.repository.TrainingRepository;
import at.happydog.test.security.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 PreloadDatabase class

 This class is preloading AppUsers and Trainings into the database
**/

@Configuration
public class PreloadDatabase {


    private static final Logger log = LoggerFactory.getLogger(PreloadDatabase.class);

    private AppUser admin = new AppUser(
            "admin",
            "admin",
            "admin",
            "admin@mail.com",
            new PasswordEncoder().bCryptPasswordEncoder().encode("admin"),
            AppUserRoles.ADMIN,
            true);

    private AppUser owner = new AppUser(
            "owner",
            "owner",
            "owner",
            "owner@mail.com",
            new PasswordEncoder().bCryptPasswordEncoder().encode("owner"),
            AppUserRoles.DOG_OWNER,
            true);

    private final AppUserRating rating1 = new AppUserRating(4, "Trainer war ganz okay");
    private final AppUserRating rating2 = new AppUserRating(5, "Hurensohn");
    private final AppUserRating rating3 = new AppUserRating(1, "Seine Frisur war scheiße!");

    private final AppUser trainer = new AppUser(
            "trainer",
            "trainer",
            "trainer",
            "trainer@mail.com",
            new PasswordEncoder().bCryptPasswordEncoder().encode("trainer"),
            AppUserRoles.DOG_TRAINER,
            new ArrayList<>(Arrays.asList(rating1, rating2, rating3)),
            new Location("Stregengasse", "6", "Graz", "8054", new BigDecimal(47.033030), new BigDecimal(15.400400)),
            true);




    private Training training1 = new Training("Hundetraining mega",
            "Trainieren sie ihren Fisch, ähh ich meine Hund!",
            35.0,
            new Date(2023 - 1900,04, 14),
            LocalTime.of(10, 15, 0),
            LocalTime.of(11,0,0),
            new Location("Graz", new BigDecimal(47.0707), new BigDecimal(15.4395)),true);

    private Training training2 = new Training("Hundetraining bonse",
            "Trainieren sie ihren Hund, gut!",
            35.0,
            new Date(2023 - 1900,4, 3),
            LocalTime.of(10, 15, 0),
            LocalTime.of(11,0,0),
            new Location("Graz", new BigDecimal(47.0707), new BigDecimal(15.4395)),true);

    private Training training3 = new Training("Hundetraining ultra",
            "Trainieren sie vielleicht ihren Hund!",
            35.0,
            new Date(2023 - 1900,6,24),
            LocalTime.of(12, 30, 0),
            LocalTime.of(13,30,0),
            new Location("Graz", new BigDecimal(47.0707), new BigDecimal(15.4395)),true);

    private Training training4 = new Training("Hundetraining Wien",
            "Trainieren sie noch morgen, meistens Hund!",
            49.99,
            new Date(2023 - 1900,6,24),
            LocalTime.of(12, 30, 0),
            LocalTime.of(13,30,0),
            new Location("Wien", new BigDecimal(48.218230), new BigDecimal(16.403300)),true);

    private Training training5 = new Training("Hundetraining Ficker",
            "Trainieren sie in Wien, aber nur wenn sie möchten.",
            29.95,
            new Date(2023 - 1900,6,24),
            LocalTime.of(12, 30, 0),
            LocalTime.of(13,30,0),
            new Location("Wien", new BigDecimal(48.2658534), new BigDecimal(16.4530547)),true);






    @Bean
    CommandLineRunner initDatabase(AppUserRepository repository, TrainingRepository trainingRepository) {
        trainer.addTraining(training1);
        trainer.addTraining(training2);
        trainer.addTraining(training3);
        trainer.addTraining(training4);
        trainer.addTraining(training5);

        TrainerFactory trainerFactory = new TrainerFactory();
        List<AppUser> trainerList = trainerFactory.createAndConfigureUser(10);


        return args -> {


            //Preload AppUsers
            log.info("Preloading " + repository.save(admin));
            log.info("Preloading " + repository.save(owner));
            log.info("Preloading " + repository.save(trainer));
            for (AppUser appUser : trainerList) {
                log.info("Preloading " + repository.save(appUser));

            }



        };
    }
}
