package com.johanna.minaRecept.controllers;


import com.johanna.minaRecept.config.AppPasswordConfig;
import com.johanna.minaRecept.models.RecipeEntity;
import com.johanna.minaRecept.models.Role;
import com.johanna.minaRecept.models.UserEntity;
import com.johanna.minaRecept.repositories.RecipeRepository;
import com.johanna.minaRecept.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;

    private final RecipeRepository recipeRepository;
    private final AppPasswordConfig appPasswordConfig;

    @Autowired
    public UserController(UserRepository userRepository, AppPasswordConfig appPasswordConfig, RecipeRepository recipeRepository) {
        this.userRepository = userRepository;
        this.appPasswordConfig = appPasswordConfig;
        this.recipeRepository = recipeRepository;
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

        // Hämta den inloggade användaren från UserRepository
        UserEntity user = userRepository.findByUsername(username);

        if (user != null) {
            // Visa välkomsthälsning oavsett om användaren har recept eller inte
            model.addAttribute("username", username);

            // Hämta de senaste tre recepten för den inloggade användaren
            List<RecipeEntity> userRecipes = recipeRepository.findTop3ByUserUsernameOrderByCreatedAtDesc(username);

            if (!userRecipes.isEmpty()) {
                // Om användaren har recept, lägg till dem i modellen för att visas på profilsidan
                model.addAttribute("recipes", userRecipes);
            } else {
                // Om användaren inte har några recept, lägg till ett meddelande om detta i modellen
                model.addAttribute("noRecipesMessage", "Inga recept hittades för användaren.");
            }
        } else {
            // Om användaren inte hittas, lägg till ett felmeddelande i modellen
            model.addAttribute("errorMessage", "Användaren kunde inte hittas.");
        }

        return "profile"; // Returnera profilsidan
    }



    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/logout-page";
    }


}
