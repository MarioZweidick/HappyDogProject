package at.happydog.test.registrationUtil;

import at.happydog.test.enity.AppUserRoles;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 Die User Registration Request die im Controller vom Frontend verlangt wird
 **/

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserRegistrationRequest {

    private final String username;
    private final String firstname;
    private final String lastname;
    private final String password;
    private final String email;
    private final AppUserRoles role;
}
