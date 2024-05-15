package com.johanna.minaRecept.config;

import com.johanna.minaRecept.services.UserEntityDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AppSecurityConfig {

    private final AppPasswordConfig appPasswordConfig;
    private final UserEntityDetailsService userEntityDetailsService;

    @Autowired
    public AppSecurityConfig(AppPasswordConfig appPasswordConfig, UserEntityDetailsService userEntityDetailsService) {
        this.appPasswordConfig = appPasswordConfig;
        this.userEntityDetailsService = userEntityDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/","/hash","/home","/login", "/register", "/profile","/add-recipe","/recipe-details","/edit-recipe","/search-results").permitAll() // Tillåt åtkomst till startsida och registreringssida utan inloggning
                        .anyRequest().authenticated() // Alla andra begäranden kräver autentisering
                )
                .formLogin(formLogin -> formLogin.loginPage("/login") // Ange URL för inloggningssida
                        .failureHandler(new CustomAuthenticationFailureHandler())
                        .defaultSuccessUrl("/profile", true))//

                .logout(logout -> logout
                        .logoutUrl("/logout-page") // Ange URL för utloggning
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID" , "remember-me")
                        .logoutSuccessUrl("/home")
                )

                .rememberMe(rememberMe -> rememberMe
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("someSecurityKey")
                .userDetailsService(userEntityDetailsService)
                .rememberMeParameter("remember-me"))
                .authenticationProvider(daoAuthenticationProvider())    // Tell Spring to use our implementation (Password & Service)
                .build();

    }

    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(appPasswordConfig.bCryptPasswordEncoder());
        provider.setUserDetailsService(userEntityDetailsService);

        return provider;
    }


}

