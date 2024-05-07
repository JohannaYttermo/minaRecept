package com.johanna.minaRecept.controllers;


import com.johanna.minaRecept.config.AppPasswordConfig;
import com.johanna.minaRecept.models.UserEntity;
import com.johanna.minaRecept.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final AppPasswordConfig appPasswordConfig;

    @Autowired
    public UserController(UserRepository userRepository, AppPasswordConfig appPasswordConfig) {
        this.userRepository = userRepository;
        this.appPasswordConfig = appPasswordConfig;
    }


    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("userEntity", new UserEntity());
        return "register"; // Returnerar namnet på din Thymeleaf-sida för registrering

    }
        @GetMapping("/logout-page")
        public String showLogoutPage() {
            // Visa logout-sidan
            return "logout-page";
        }

}
