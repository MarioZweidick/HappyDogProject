package at.happydog.test.repository;


import at.happydog.test.enity.AppUserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 AppUserImageRepository
 **/

@Repository
public interface AppUserImageRepository extends JpaRepository<AppUserImage, Long> {

    Optional<AppUserImage> findByName(String filename);

}
