package at.happydog.test.security.config;

import at.happydog.test.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 Custom WebSecurityConfig - Die Default Security Config wurde deaktiviert

 Lookup Spring Security Docs für mehr infos

 !!Please don't change anything without calling me xd!!
 **/

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                .loginPage("/user/login").permitAll()
                .successForwardUrl("/")
                //.failureForwardUrl("/user/login")
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/logout-success").permitAll()
                .and()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/*").permitAll()
                        .requestMatchers("/user/login").permitAll()
                        .requestMatchers( "/user/registration/**").permitAll()
                        .requestMatchers("/user/profile/**").hasAnyAuthority("DOG_OWNER", "DOG_TRAINER")
                        .anyRequest().authenticated()
                );

        return http.build();
    }


    //Authentication manager - DB Authentication mit DAO Auth Provider
    //Gibt gute Spring Security bezüglich unterschied zwischen AuthManager und Provider etc.
    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(appUserService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }


}
