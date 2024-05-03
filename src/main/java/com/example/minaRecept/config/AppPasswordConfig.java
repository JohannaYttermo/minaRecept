package com.example.minaRecept.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

    @Configuration
    @Component
    public class AppPasswordConfig {


        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {

            return new BCryptPasswordEncoder(10);     // Default is 10
        }

    }

