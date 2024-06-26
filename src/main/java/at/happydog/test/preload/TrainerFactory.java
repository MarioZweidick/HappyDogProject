package at.happydog.test.preload;

import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.AppUserRoles;
import at.happydog.test.enity.Location;
import at.happydog.test.enity.Training;
import at.happydog.test.security.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TrainerFactory {

    public  List<AppUser> createAndConfigureUser(int amount)
    {
        TrainingFactory trainingFactory = new TrainingFactory();
        List<Training> trainingsList = trainingFactory.createTraining(3);

        List<AppUser> returnConfigeredUserList = new ArrayList<>();
        for(int i = 0; i<amount-1;i++)
        {
           AppUser trainer = new AppUser(
                "trainer"+ i,
                "trainer" + i,
                "trainer"+ i,
                "trainer@mail.com"+i,
                new PasswordEncoder().bCryptPasswordEncoder().encode("trainer"),
                AppUserRoles.DOG_TRAINER,
                null,
                new Location("Stregengasse", "6", "Graz", "8054", new BigDecimal(47.033030), new BigDecimal(15.400400)),
                true,"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   \n" +
                   "\n" +
                   "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   \n" +
                   "\n" +
                   "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.   \n" +
                   "\n" +
                   "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer");

           /*for (Training training: trainingsList)
           {
               trainer.addTraining(training);
           }
           returnConfigeredUserList.add(trainer);*/

        }
        return returnConfigeredUserList;
    }


}
