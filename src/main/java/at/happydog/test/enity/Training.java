package at.happydog.test.enity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Transactional
@Table
public class Training {

    @Id
    @SequenceGenerator(
            name = "training_id_sequence",
            sequenceName = "training_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "training_id_sequence"
    )
    @Column(name = "training_id")
    private Long training_id;

    private String titel;
    private String description;
    private Double price;
    private Date date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_location_id")
    private Location location;

    public Training() {

    }

    public Training(String titel, String description, Double price) {
        this.titel = titel;
        this.description = description;
        this.price = price;
    }
}
