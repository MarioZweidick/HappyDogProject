package at.happydog.test.preload;

import at.happydog.test.Handler.ImageLoader;
import at.happydog.test.enity.Location;
import at.happydog.test.enity.Training;
import at.happydog.test.enity.UserImages;

import javax.swing.plaf.IconUIResource;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainingFactory {

    String descriptionTraining1;
    String descriptionTraining2;
    String descriptionTraining3;

    public TrainingFactory() {
        descriptionTraining1 = "Agility-Training ist ein dynamischer Sport für Hunde, bei dem sie einen Parcours mit Hindernissen schnell und präzise bewältigen müssen.";
        descriptionTraining2 = "Erleben Sie das ultimative Anfängertraining für Hunde! Belohnungsbasiertes Training mit positiver Verstärkung. Einfache Kommandos wie \"Sitz\" und \"Platz\".";
        descriptionTraining3 = "Entdecken Sie unser fortgeschrittenes Hundetraining - die ultimative Möglichkeit, Ihr Haustier auf ein neues Level zu bringen!";


    }

    public List<Training> createTraining(int number) {
        List<Training> trainingList = new ArrayList<>();
        UserImages trainingsImage1 = ImageLoader.createJPEGUserImage("src/main/resources/servtecPhotos/Training/agilityTraining.jpg");
        UserImages trainingsImage2 = ImageLoader.createJPEGUserImage("src/main/resources/servtecPhotos/Training/AnfängerTraining.jpg");
        // UserImages trainingsImage3 = ImageLoader.createUserImage("src/main/resources/servtecPhotos/Training/FotgeschrittenHundetraining.png");


        for (int i = 0; i < number; i++) {
            switch (i % 3) {

                case 0:

                    Training training1 = new Training("Agility Training",
                            descriptionTraining1,
                            35.0,
                            new Date(2023 - 1900, 04, 14),
                            LocalTime.of(10, 15, 0),
                            LocalTime.of(11, 0, 0),
                            new Location("Graz", new BigDecimal(47.0707), new BigDecimal(15.4395)), true);
                    trainingList.add(training1);
                    continue;

                case 1:
                    Training training2 = new Training("Beginner Training",
                            descriptionTraining2,
                            35.0,
                            new Date(2023 - 1900, 04, 14),
                            LocalTime.of(10, 15, 0),
                            LocalTime.of(11, 0, 0),
                            new Location("Graz", new BigDecimal(47.0707), new BigDecimal(15.4395)), true);
                    trainingList.add(training2);
                    continue;

                case 2:
                    Training training3 = new Training("Fortgeschrittenen Training",
                            descriptionTraining3,
                            35.0,
                            new Date(2023 - 1900, 04, 14),
                            LocalTime.of(10, 15, 0),
                            LocalTime.of(11, 0, 0),
                            new Location("Graz", new BigDecimal(47.0707), new BigDecimal(15.4395)), true);
                    trainingList.add(training3);

            }

        }
        return trainingList;
    }
}
