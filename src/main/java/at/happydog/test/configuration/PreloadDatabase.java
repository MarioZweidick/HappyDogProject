package at.happydog.test.configuration;

import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.AppUserRoles;
import at.happydog.test.enity.Location;
import at.happydog.test.enity.Training;
import at.happydog.test.repository.AppUserRepository;
import at.happydog.test.repository.TrainingRepository;
import at.happydog.test.security.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private AppUser trainer = new AppUser(
            "trainer",
            "trainer",
            "trainer",
            "trainer@mail.com",
            new PasswordEncoder().bCryptPasswordEncoder().encode("trainer"),
            AppUserRoles.DOG_TRAINER,
            true);

    private Training training1 = new Training("Hundetraining mega",
            "Trainieren sie ihren Fisch, ähh ich meine Hund!",
            35.0,
            new Date(2023 - 1900,04, 14),
            LocalTime.of(10, 15, 0),
            LocalTime.of(11,0,0),
            new Location("Graz", "47.0707", "15.4395"));

    private Training training2 = new Training("Hundetraining bonse",
            "Trainieren sie ihren Hund, gut!",
            35.0,
            new Date(2023 - 1900,4, 3),
            LocalTime.of(10, 15, 0),
            LocalTime.of(11,0,0),
            new Location("Graz", "47.0707", "15.4395"));

    private Training training3 = new Training("Hundetraining ultra",
            "Trainieren sie vielleicht ihren Hund!",
            35.0,
            new Date(2023 - 1900,6,24),
            LocalTime.of(12, 30, 0),
            LocalTime.of(13,30,0),
            new Location("Graz", "47.0707", "15.4395"));



    @Bean
    CommandLineRunner initDatabase(AppUserRepository repository, TrainingRepository trainingRepository) {



        return args -> {


            log.info("Preloading " + trainingRepository.save(training1));
            log.info("Preloading " + trainingRepository.save(training2));
            log.info("Preloading " + trainingRepository.save(training3));



            //Preload AppUsers
            log.info("Preloading " + repository.save(admin));
            log.info("Preloading " + repository.save(owner));
            log.info("Preloading " + repository.save(trainer));

        };
    }
}
