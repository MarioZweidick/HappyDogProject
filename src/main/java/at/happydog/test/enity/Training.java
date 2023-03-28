package at.happydog.test.enity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalTime;
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

    private LocalTime beginnTime;

    private LocalTime endTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_location_id")
    private Location location;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_appuser_id", referencedColumnName = "appuser_id")
    private AppUser appUser;

    public Training() {


    }

    public Training(String titel, String description, Double price, Date date, LocalTime beginnTime, LocalTime endTime, Location location) {
        this.titel = titel;
        this.description = description;
        this.price = price;
        this.date = date;
        this.beginnTime = beginnTime;
        this.endTime = endTime;
        this.location = location;
    }

    public Training(String titel, String description, Double price) {
        this.titel = titel;
        this.description = description;
        this.price = price;
    }


}
