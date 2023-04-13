package at.happydog.test.enity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Transactional
@Table
@NoArgsConstructor
public class Booking {
    @Id
    @SequenceGenerator(
            name = "booking_id_sequence",
            sequenceName = "booking_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "booking_id_sequence"
    )
    @Column(name = "booking_id")
    private Long booking_id;



    private Long offerer;
    private Long buyer;

    @OneToOne
    @JoinColumn(name = "training_training_id")
    private Training training;


    public Booking(Long offerer, Long buyer, Training training) {
        this.offerer = offerer;
        this.buyer = buyer;
        this.training = training;
    }
}
