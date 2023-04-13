package at.happydog.test.repository;

import at.happydog.test.enity.AppUserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRatingRepository extends JpaRepository<AppUserRating, Long> {


}
