package com.johanna.minaRecept.controllers;


import com.johanna.minaRecept.config.AppPasswordConfig;
import com.johanna.minaRecept.models.Role;
import com.johanna.minaRecept.models.UserEntity;
import com.johanna.minaRecept.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

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

    @PostMapping("/register")
    public String registerUser(
            @Valid UserEntity userEntity,
            BindingResult bindingResult,
            @RequestParam(name = "role", defaultValue = "USER") String roleName, // Används för att hämta rollen från förfrågan
            RedirectAttributes redirectAttributes) { // Används för att skicka meddelande till login-sidan

        if (bindingResult.hasErrors()) {
            return "register";
        }

        // Kontrollera om rollen finns, annars använd standardrollen "USER"
        Role role = Role.valueOf(roleName.toUpperCase());

        // Inställningar för användarattribut
        userEntity.setPassword(appPasswordConfig.bCryptPasswordEncoder().encode(userEntity.getPassword()));
        userEntity.setAccountEnabled(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setAccountNonExpired(true);
        userEntity.setCredentialsNonExpired(true);
        userEntity.setRole(role); // Sätt användarrollen

        // Spara användaren i databasen
        userRepository.save(userEntity);

        // Skicka meddelande till login-sidan endast om användaren just har registrerats
        redirectAttributes.addFlashAttribute("registrationSuccess", true);

        // Omdirigera till login-sidan
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String showProfilePage(Principal principal, Model model) {
        String username = principal.getName(); // Hämta användarnamnet från inloggad användare
        model.addAttribute("username", username); // Lägg till användaren i modellen för att visa i profilsidan
        return "profile";
    }




    @GetMapping("/logout-page")
        public String showLogoutPage() {
            // Visa logout-sidan
            return "logout-page";
        }

}
