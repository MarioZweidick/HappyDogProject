package at.happydog.test.enity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Transactional
@Table
public class AppUserRating {
    @Id
    @SequenceGenerator(
            name = "rating_id_sequence",
            sequenceName = "rating_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rating_id_sequence"
    )
    @Column(name = "rating_id")
    private Long rating_id;

    private Integer rating;
    private String comment;

    @OneToOne
    @JoinColumn(name = "from_app_user_appuser_id")
    private AppUser fromAppUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appuser_id")
    private AppUser toAppUser;

    public AppUserRating() {

    }

    public AppUserRating(Integer rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public AppUserRating(Integer rating, String comment, AppUser toAppUser) {
        this.rating = rating;
        this.comment = comment;
        this.toAppUser = toAppUser;
    }

    public AppUserRating(Integer rating, String comment, AppUser fromAppUser, AppUser toAppUser) {
        this.rating = rating;
        this.comment = comment;
        this.fromAppUser = fromAppUser;
        this.toAppUser = toAppUser;
    }
}
