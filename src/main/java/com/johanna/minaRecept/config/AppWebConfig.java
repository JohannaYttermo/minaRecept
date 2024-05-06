package com.johanna.minaRecept.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


    @Configuration  // Allows Spring to find this config
    @EnableWebMvc
    public class AppWebConfig implements WebMvcConfigurer {

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("home");
            registry.addViewController("/register").setViewName("register");
            registry.addViewController("/login").setViewName("login");
            registry.addViewController("/welcome").setViewName("welcome");
            registry.addViewController("/logout").setViewName("logout");
            registry.addViewController("/create-list").setViewName("create-list");
            registry.addViewController("/logout-page").setViewName("logout-page");
        }
    }

