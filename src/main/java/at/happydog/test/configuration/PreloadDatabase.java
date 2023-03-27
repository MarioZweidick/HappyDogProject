package at.happydog.test.configuration;

import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.AppUserRoles;
import at.happydog.test.repository.AppUserRepository;
import at.happydog.test.security.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PreloadDatabase {

    private static final Logger log = LoggerFactory.getLogger(PreloadDatabase.class);


    @Bean
    CommandLineRunner initDatabase(AppUserRepository repository) {

        return args -> {


            //Preload AppUsers
            log.info("Preloading " + repository.save(new AppUser(
                    "trainer",
                    "trainer",
                    "trainer",
                    "trainer@mail.com",
                    new PasswordEncoder().bCryptPasswordEncoder().encode("trainer"),
                    AppUserRoles.DOG_TRAINER,
                    true)));

            log.info("Preloading " + repository.save(new AppUser(
                    "owner",
                    "owner",
                    "owner",
                    "owner@mail.com",
                    new PasswordEncoder().bCryptPasswordEncoder().encode("owner"),
                    AppUserRoles.DOG_OWNER,
                    true)));

            log.info("Preloading " + repository.save(new AppUser(
                    "admin",
                    "admin",
                    "admin",
                    "admin@mail.com",
                    new PasswordEncoder().bCryptPasswordEncoder().encode("admin"),
                    AppUserRoles.ADMIN,
                    true)));


        };
    }
}
