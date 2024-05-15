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
            registry.addViewController("/profile").setViewName("profile");
            registry.addViewController("/add-recipe").setViewName("add-recipe");
            registry.addViewController("/edit-recipe").setViewName("edit-recipe");
            registry.addViewController("/recipe-details").setViewName("recipe-details");
            registry.addViewController("/search-results").setViewName("search-results");
            registry.addViewController("/logout").setViewName("logout");
            registry.addViewController("/logout-page").setViewName("logout-page");
        }
    }

