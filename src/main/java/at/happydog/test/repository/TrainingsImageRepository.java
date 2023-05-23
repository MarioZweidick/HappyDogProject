package at.happydog.test.repository;

import at.happydog.test.enity.UserImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TrainingsImageRepository extends JpaRepository<UserImages, Long> {
    Optional<UserImages> findByName(String filename);
}
