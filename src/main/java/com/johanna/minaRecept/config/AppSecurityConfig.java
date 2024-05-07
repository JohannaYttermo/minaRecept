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
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/","/home","/login", "/register", "/profile").permitAll() // Tillåt åtkomst till startsida och registreringssida utan inloggning
                        .anyRequest().authenticated() // Alla andra begäranden kräver autentisering
                )
                .formLogin(formLogin -> formLogin.loginPage("/login") // Ange URL för inloggningssida
                        .failureHandler(authenticationFailureHandler())
                        .defaultSuccessUrl("/profile", true))//

                .logout(logout -> logout
                        .logoutUrl("/logout-page") // Ange URL för utloggning
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID" , "remember-me")
                        .logoutSuccessUrl("/home")
                );

        return http.build();
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
}



