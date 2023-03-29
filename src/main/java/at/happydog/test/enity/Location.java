package at.happydog.test.enity;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

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

    private String name;
    private String N; //Latitude
    private String E; //Longitude

    public Location() {

    }

    public Location(String name, String n, String e) {
        this.name = name;
        N = n;
        E = e;
    }
}
