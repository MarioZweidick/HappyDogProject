package at.happydog.test.enity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 Training entity
 **/

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

    private Boolean visible;
    private Boolean isBooked = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_location_id")
    private Location location;

    @ManyToMany(mappedBy = "trainings")
    private List<AppUser> appUsers = new ArrayList<>();

    private Long buyer_id;

    public Training() {


    }

    public Training(String titel, String description, Double price, Date date, LocalTime beginnTime, LocalTime endTime, Location location, Boolean visible) {
        this.titel = titel;
        this.description = description;
        this.price = price;
        this.date = date;
        this.beginnTime = beginnTime;
        this.endTime = endTime;
        this.location = location;
        this.visible = visible;
    }

    public Training(String titel, String description, Double price) {
        this.titel = titel;
        this.description = description;
        this.price = price;
    }

    public void addAppUser(AppUser appUser) {
        appUsers.add(appUser);
    }
}
