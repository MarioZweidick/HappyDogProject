package at.happydog.test.enity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 AppUserImage entity
 **/

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table
@Transactional
public class AppUserImage {

    @Id
    @SequenceGenerator(
            name = "appuserimage_id_sequence",
            sequenceName = "appuserimage_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appuserimage_id_sequence"
    )
    private Long appuserimage_id;

    private String name;
    private String type;


    @Lob
    @Column(name = "imagedata")
    private byte[] imageData;

    public AppUserImage() {
        imageData = new byte[0];
    }
}
