package at.happydog.test.preload;

import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.AppUserImage;
import at.happydog.test.enity.AppUserRoles;
import at.happydog.test.enity.Location;
import at.happydog.test.security.PasswordEncoder;
import javassist.expr.Cast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainerFactory {

    public  List<AppUser> createAndConfigureUser(int amount)
    {
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
                true);
           returnConfigeredUserList.add(trainer);

        }
        return returnConfigeredUserList;
    }
}
