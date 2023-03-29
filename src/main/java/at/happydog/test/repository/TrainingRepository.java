package at.happydog.test.repository;


import at.happydog.test.enity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 TrainingRepository
 **/

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Override
    Optional<Training> findById(Long id);

}
