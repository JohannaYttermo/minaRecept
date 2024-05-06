package com.johanna.minaRecept.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/","/home","/login", "/register").permitAll() // Tillåt åtkomst till startsida och registreringssida utan inloggning
                        .anyRequest().authenticated() // Alla andra begäranden kräver autentisering
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // Ange URL för inloggningssida
                        .failureHandler(authenticationFailureHandler()) // Ange anpassad felhanterare för inloggning
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Ange URL för utloggning
                        .permitAll()
                )
                .csrf().disable(); // Inaktivera CSRF-skydd för enkelhetens skull

        return http.build();
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
}



