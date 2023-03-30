package at.happydog.test.service;

import at.happydog.test.enity.Location;
import at.happydog.test.enity.Training;
import at.happydog.test.repository.LocationRepository;
import at.happydog.test.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final TrainingRepository trainingRepository;



    @Autowired
    public LocationService(LocationRepository locationRepository, TrainingRepository trainingRepository) {
        this.locationRepository = locationRepository;
        this.trainingRepository = trainingRepository;
    }

    public Location save(Location location){
        return locationRepository.save(location);
    }

    public Location getLocationById(Long id){
        Optional<Location> location = locationRepository.findById(id);
        return location.orElse(null);
    }

    public Boolean findLocation(BigDecimal lat, BigDecimal lng){
        List<Location> locations = locationRepository.findAll();

        for (Location loc: locations) {
            if(loc.getN().equals(lat) && loc.getE().equals(lng)){
                return true;
            }
        }
        return false;
    }

    public Location inputMatcher(String location){
        String newLoc = location.replaceAll("\\s+",",");
        String[] parts = newLoc.split(",");

        List<Location> locationList = new ArrayList<>();
        for (String s:parts) {
            locationList.addAll(locationRepository.searchLocations(s.toLowerCase()));
        }


        if(!locationList.isEmpty()){
            return locationList.get(0);
        }

        return null;
    }

    public List<Training> getTrainingsInRange(BigDecimal Lat, BigDecimal Lng, Double km){

        List<Training> trainings = trainingRepository.findAll();

        double N = Lat.doubleValue();
        double E = Lng.doubleValue();

        BigDecimal latRange = getLatRange(Lat, km).abs();
        BigDecimal lngRange = getLngRange(Lng, km).abs();

        List<Training> trainingsInRange = new ArrayList<>();

        for (Training t:trainings) {
            double tN = t.getLocation().getN().doubleValue();
            double tE = t.getLocation().getE().doubleValue();

            // Check if the training's N and E values are within the range of the given location
            if (tN >= (N - latRange.doubleValue()) && tN <= (N + latRange.doubleValue()) &&
                    tE >= (E - lngRange.doubleValue()) && tE <= (E + lngRange.doubleValue())) {
                trainingsInRange.add(t);
            }
        }

        return trainingsInRange;

    }

    public BigDecimal getLatRange(BigDecimal N, Double km){
        //Example:
        //Input: N, 50
        //Output: 50km in degrees for that Latitude
       return new BigDecimal(km / (111.32 * Math.cos(N.doubleValue())));
    }


    public BigDecimal getLngRange(BigDecimal E, Double km){
        //Example:
        //Input: E, 50
        //Output: 50km in degrees for that Longitude
        //This function takes the curvature of the earth into account
        double latRad = Math.toRadians(E.doubleValue());
        double a = 6378.137; // equatorial radius of the earth in kilometers
        double b = 6356.7523; // polar radius of the earth in kilometers
        double cosLatRad = Math.cos(latRad);
        double sinLatRad = Math.sin(latRad);
        double eSq = (a * a - b * b) / (a * a);
        double deltaLng = km / (a * cosLatRad * 2 * Math.PI / 360);
        double lngRange = deltaLng / Math.sqrt(1 - eSq * sinLatRad * sinLatRad);
        return new BigDecimal(lngRange);
    }

}
