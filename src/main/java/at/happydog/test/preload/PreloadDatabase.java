package at.happydog.test.preload;

import at.happydog.test.Handler.ImageLoader;
import at.happydog.test.enity.*;
import at.happydog.test.repository.AppUserImageRepository;
import at.happydog.test.repository.AppUserRepository;
import at.happydog.test.repository.TrainingRepository;
import at.happydog.test.security.PasswordEncoder;
import at.happydog.test.service.AppUserService;
import at.happydog.test.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * PreloadDatabase class
 * <p>
 * This class is preloading AppUsers and Trainings into the database
 **/

@Configuration
public class PreloadDatabase {


    private static final Logger log = LoggerFactory.getLogger(PreloadDatabase.class);

    private String trainerDescription1 = "Als erfahrener Hundetrainer mit fundiertem Hintergrundwissen in Verhaltenspsychologie baue ich eine starke Bindung zu den Hunden auf und wende effektive Trainingsmethoden an. Meine ruhige und geduldige Art ermöglicht es mir, schwierige Verhaltensprobleme zu lösen und individuelle Bedürfnisse zu berücksichtigen.";
    private String trainerDescription2 = "Mit meiner positiven und motivierenden Trainingsmethode schaffe ich eine angenehme Atmosphäre im Training. Ich motiviere Hunde mit viel Energie und Begeisterung und bringe ihnen neue Tricks und Kommandos bei. Meine interaktiven Übungen sind sowohl für Hunde als auch für ihre Besitzer unterhaltsam.";
    private String trainerDescription3 = "Als Expertin für Welpentraining und soziale Interaktion schaffe ich eine sichere Umgebung, in der junge Hunde lernen und wachsen können. Mit einem liebevollen und einfühlsamen Ansatz konzentriere ich mich auf Prägung, Bindungsentwicklung und frühe Gehorsamkeit. Ich gebe den Besitzern wertvolle Tipps, um die besten Ergebnisse aus der Trainingseinheit zu erzielen.";
    private String trainerDescription4 = "Durch meine Spezialisierung auf Hundesport und Leistungstraining unterstütze ich Hunde bei der Vorbereitung auf Wettbewerbe und Wettkämpfe. Mit meiner hohen Fachkenntnis in verschiedenen Disziplinen wie Agility, Obedience und Flyball helfe ich Hunden, ihr volles Potenzial auszuschöpfen und Spitzenleistungen zu erzielen. Mein strukturiertes Training, meine technischen Kenntnisse und mein Fokus auf Details tragen dazu bei.";
    private String trainerDescription5 = "Als einfühlsame Verhaltenstherapeutin für Hunde mit Verhaltensproblemen entwickle ich individuelle Trainingspläne, um diese Probleme anzugehen. Ich habe ein tiefes Verständnis für die Ursachen von unerwünschtem Verhalten und setze positive Verstärkung und Verhaltensmodifikation ein, um den Hunden zu helfen, Vertrauen aufzubauen und ihre Verhaltensweisen zu verbessern. Meine ruhige Präsenz schafft eine unterstützende Umgebung für die Hunde und ihre Besitzer.";

    private String descriptionTraining1 = "Agility-Training ist ein dynamischer Sport für Hunde, bei dem sie einen Parcours mit Hindernissen schnell und präzise bewältigen müssen.";
    private String descriptionTraining2 = "Erleben Sie das ultimative Anfängertraining für Hunde! Belohnungsbasiertes Training mit positiver Verstärkung. Einfache Kommandos wie \"Sitz\" und \"Platz\".";
    private String descriptionTraining3 = "Entdecken Sie unser fortgeschrittenes Hundetraining - die ultimative Möglichkeit, Ihr Haustier auf ein neues Level zu bringen!";



    private AppUser admin = new AppUser(
            "admin",
            "admin",
            "admin",
            "admin@mail.com",
            new PasswordEncoder().bCryptPasswordEncoder().encode("admin"),
            AppUserRoles.ADMIN,
            true);

    private AppUser owner = new AppUser(
            "Patrick",
            "kainer",
            "kainer",
            "kainer.patrick@gmail.com",
            new PasswordEncoder().bCryptPasswordEncoder().encode("owner"),
            AppUserRoles.DOG_OWNER,
            true);


    private final AppUser trainer1 = new AppUser(
            "trainer1",
            "Hannah",
            "Mayer",
            "trainer1@mail.com",
            new PasswordEncoder().bCryptPasswordEncoder().encode("trainer"),
            AppUserRoles.DOG_TRAINER,
            null,
            new Location("Stregengasse", "6", "Graz", "8054", new BigDecimal(47.033030), new BigDecimal(15.400400)),
            true, trainerDescription1);

    private final AppUser trainer2 = new AppUser(
            "trainer2",
            "Laura",
            "Schneider",
            "trainer2@mail.com",
            new PasswordEncoder().bCryptPasswordEncoder().encode("trainer"),
            AppUserRoles.DOG_TRAINER,
            null,
            new Location("Alterilz Weg", "28", "Weiz", "8160", new BigDecimal(47.22828699999999), new BigDecimal(15.6864071)),
            true, trainerDescription1);

    private final AppUser trainer3 = new AppUser(
            "trainer3",
            "Franz",
            "Rudolf",
            "trainer3@mail.com",
            new PasswordEncoder().bCryptPasswordEncoder().encode("trainer"),
            AppUserRoles.DOG_TRAINER, null,
            new Location("Stregengasse", "6", "Graz", "8054", new BigDecimal(47.033031), new BigDecimal(15.400401)),
            true, trainerDescription3);

    private final AppUser trainer4 = new AppUser(
            "trainer4",
            "Ricardo",
            " Delgado",
            "trainer@mail.com",
            new PasswordEncoder().bCryptPasswordEncoder().encode("trainer"),
            AppUserRoles.DOG_TRAINER,
            null,
            new Location("Stregengasse", "6", "Havanna", "8054", new BigDecimal(23.1135925), new BigDecimal(-82.3665956)),
            true, trainerDescription4);

    private final AppUser trainer5 = new AppUser(
            "trainer5",
            "Tom",
            "Wagner",
            "trainer@mail.com",
            new PasswordEncoder().bCryptPasswordEncoder().encode("trainer"),
            AppUserRoles.DOG_TRAINER,
            null,
            new Location("Stregengasse", "6", "Graz", "8054", new BigDecimal(47.033030), new BigDecimal(15.400400)),
            true, trainerDescription5);

    @Bean
    CommandLineRunner initDatabase(AppUserRepository repository, AppUserService appUserService, TrainingService trainingService) {


        TrainingFactory trainingFactory = new TrainingFactory();
        List<Training> trainingList = trainingFactory.createTraining(15);

        RatingFactory ratingFactory = new RatingFactory();
        List<AppUserRating> ratingList1 = ratingFactory.createRating(3);
        List<AppUserRating> ratingList2 = ratingFactory.createRating(3);
        List<AppUserRating> ratingList3 = ratingFactory.createRating(3);
        List<AppUserRating> ratingList4 = ratingFactory.createRating(3);
        List<AppUserRating> ratingList5 = ratingFactory.createRating(3);

        trainer1.setRatings(ratingList1);
        trainer2.setRatings(ratingList2);
        trainer3.setRatings(ratingList3);
        trainer4.setRatings(ratingList4);
        trainer5.setRatings(ratingList5);

        for (int i = 0; i<trainingList.size();i++) {

            if(i<3) {
                trainer1.addTraining(trainingList.get(i));
                            }else if(i<6)
                trainer2.addTraining(trainingList.get(i));
            else if(i<9)
                trainer3.addTraining(trainingList.get(i));
            else if(i<12)
                trainer4.addTraining(trainingList.get(i));
            else
                trainer5.addTraining(trainingList.get(i));
        }

        trainer1.setAverageRating(4);
        trainer2.setAverageRating(4.8);
        trainer3.setAverageRating(2.7);
        trainer4.setAverageRating(3.9);
        trainer5.setAverageRating(4.2);


        try {

            appUserService.addAppUserImage(trainer1, ImageLoader.createMultiPartFromJpeg("src/main/resources/servtecPhotos/Trainer/Trainer1.jpg"));
            appUserService.addAppUserImage(trainer2, ImageLoader.createMultiPartFromJpeg("src/main/resources/servtecPhotos/Trainer/Trainer2.jpg"));
            appUserService.addAppUserImage(trainer3, ImageLoader.createMultiPartFromJpeg("src/main/resources/servtecPhotos/Trainer/Trainer3.jpg"));
            appUserService.addAppUserImage(trainer4, ImageLoader.createMultiPartFromJpeg("src/main/resources/servtecPhotos/Trainer/Trainer4.png"));
            appUserService.addAppUserImage(trainer5, ImageLoader.createMultiPartFromJpeg("src/main/resources/servtecPhotos/Trainer/Trainer5.png"));
            appUserService.addAppUserImage(owner, ImageLoader.createMultiPartFromJpeg("src/main/resources/servtecPhotos/owner/1644157411838.jpg"));

            for (Training training:trainingList) {

                if (training.getTitel() == "Agility Training")
                {
                    trainingService.addTrainingImage(training,ImageLoader.createMultiPartFromJpeg("src/main/resources/servtecPhotos/Training/agilityTraining.jpg"));
                }else if(training.getTitel() == "Beginner Training")
                {
                    trainingService.addTrainingImage(training,ImageLoader.createMultiPartFromJpeg("src/main/resources/servtecPhotos/Training/AnfängerTraining.jpg"));
                } else if (training.getTitel() == "Fortgeschrittenen Training") {
                    trainingService.addTrainingImage(training,ImageLoader.createMultiPartFromJpeg("src/main/resources/servtecPhotos/Training/FotgeschrittenHundetraining.png"));

                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return args -> {


            repository.save(admin);
            repository.save(owner);
            repository.save(trainer1);
            repository.save(trainer2);
            repository.save(trainer3);
            repository.save(trainer4);
            repository.save(trainer5);


        };
    }


}
