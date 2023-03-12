package at.happydog.test.enity;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

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
    private Double x;
    private Double y;

    public Location() {

    }

    public Location(String name, Double x, Double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
}
