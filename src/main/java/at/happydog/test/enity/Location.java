package at.happydog.test.enity;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.math.BigDecimal;

/**
 Location entity
 **/

@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Transactional
@Table
public class Location {


    @Id
    @SequenceGenerator(
            name = "location_id_sequence",
            sequenceName = "location_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "location_id_sequence"
    )
    @Column(name = "location_id")
    private Long location_id;

    private String street;
    private String streetNumber;
    private String city;
    private String plz;

    private BigDecimal N; //Latitude
    private BigDecimal E; //Longitude

    public Location() {

    }

    public Location(String city, BigDecimal n, BigDecimal e) {
        this.street = city;
        N = n;
        E = e;
    }

    public Location(String street, String streetNumber, String city, String plz, BigDecimal n, BigDecimal e) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.plz = plz;
        N = n;
        E = e;
    }
}
