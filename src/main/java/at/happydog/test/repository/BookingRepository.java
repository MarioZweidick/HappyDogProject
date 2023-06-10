package at.happydog.test.repository;

import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.Booking;
import at.happydog.test.enity.Training;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Booking b WHERE b.training.training_id = ?1")
    void deleteByTrainingId(Long trainingId);

    @Query("SELECT b FROM Booking b WHERE b.training.training_id = ?1")
    Optional<Booking> findByTrainingId(Long trainingId);

}
