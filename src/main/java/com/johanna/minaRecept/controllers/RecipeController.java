package com.johanna.minaRecept.controllers;

import com.johanna.minaRecept.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.johanna.minaRecept.models.RecipeEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
public class RecipeController {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    @GetMapping("/add-recipe")
    public String showAddRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeEntity());
        return "add-recipe";
    }


    @PostMapping("/add-recipe")
    public String saveRecipe(@ModelAttribute("recipe") RecipeEntity recipe) {
        recipeRepository.save(recipe);
        System.out.println("recept sparas i databas");
        return "redirect:/profile";
    }

    @PostMapping("/deleteRecipe")
    public String deleteRecipe(@ModelAttribute("recipeId") Long recipeId) {

        recipeRepository.deleteById(recipeId);

        return "redirect:profile";
    }



    @GetMapping("/profile")
    public String showProfile(Model model) {
        List<RecipeEntity> recipes = recipeRepository.findAll();
        model.addAttribute("recipes", recipes);
        return "profile";
    }



    @GetMapping("/recipe/{id}")
    public String showRecipeDetails(@PathVariable("id") Long id, Model model) {
        // Hämta receptet från databasen med det givna ID:t
        Optional<RecipeEntity> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            RecipeEntity recipe = optionalRecipe.get();
            // Lägg till receptet i modellen för att visa det i vyn
            model.addAttribute("recipe", recipe);
            return "recipe-details"; // Returnera namnet på Thymeleaf-sidan för detaljerad vy
        } else {
            // Om receptet inte hittas, omdirigera till en felhanteringssida eller visa ett felmeddelande
            return "error";
        }
    }


}




