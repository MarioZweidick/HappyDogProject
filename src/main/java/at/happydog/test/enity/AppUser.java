package at.happydog.test.enity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 AppUser entity
 **/

@Entity
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Transactional
@Table
public class AppUser implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "appuser_id_sequence",
            sequenceName = "appuser_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appuser_id_sequence"
    )
    @Column(name = "appuser_id")
    private Long appuser_id;

    private String username;

    @Column(columnDefinition = "text")
    private String description;

    private String firstname;
    private String lastname;
    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private AppUserRoles role;

    //OnetoOne Datenbank entry - User profile image
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_appuserimage_id")
    private UserImages userImages;

    //OnetoMany Datenbank entry - Ein User kann mehrere Trainings haben
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "appuser_training",
            joinColumns = @JoinColumn(name = "fk_appuser_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_training_id"))
    private List<Training> trainings = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_trainer_id")
    private List<AppUserRating> ratings = new ArrayList<>();


    //OnetoOne Datenbank entry - Ein User kann eine Location
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_location_id")
    private Location location;


    private Boolean locked = false;
    private Boolean enabled = false;

    public AppUser() {


    }

    public AppUser(String username, String firstname, String lastname, String email, String password, AppUserRoles role) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.locked = locked;
        this.enabled = enabled;
    }

    public AppUser(String username, String firstname, String lastname, String email, String password, AppUserRoles role, Boolean enabled) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    public AppUser(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public AppUser(String username, String firstname, String lastname, String email, String password, AppUserRoles role, List<AppUserRating> ratings, Location location, Boolean enabled) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ratings = ratings;
        this.location = location;
        this.enabled = enabled;
    }

    public AppUser(String username, String firstname, String lastname, String email, String password, AppUserRoles role, List<AppUserRating> ratings, Location location, Boolean enabled,String description) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ratings = ratings;
        this.location = location;
        this.enabled = enabled;
        this.description = description;
    }

    public boolean addTraining(Training training){
        return trainings.add(training);
    }

    public boolean deleteTraining(Training training){
        return trainings.remove(training);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "username='" + username + '\'' +
                '}';
    }
}
