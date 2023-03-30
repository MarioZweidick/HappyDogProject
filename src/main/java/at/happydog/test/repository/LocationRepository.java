package at.happydog.test.repository;

import at.happydog.test.enity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Override
    Optional<Location> findById(Long id);


    @Override
    List<Location> findAll();


    @Query("SELECT loc FROM Location loc WHERE LOWER(loc.city)  = ?1 OR lower(loc.street)  = ?1")
    List<Location> searchLocations(String location);

}
