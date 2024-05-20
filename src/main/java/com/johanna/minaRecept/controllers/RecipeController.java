package com.johanna.minaRecept.controllers;

import com.johanna.minaRecept.models.UserEntity;
import com.johanna.minaRecept.repositories.RecipeRepository;
import com.johanna.minaRecept.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.johanna.minaRecept.models.RecipeEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/add-recipe")
    public String showAddRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeEntity());
        return "add-recipe";
    }

    @PostMapping("/edit-recipe/{id}")
    public String showEditRecipeForm(@PathVariable("id") Long id, Model model) {
        Optional<RecipeEntity> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            RecipeEntity recipe = optionalRecipe.get();
            model.addAttribute("recipe", recipe);
            return "edit-recipe";
        } else {
            return "error";
        }
    }

    @PostMapping("/save-recipe")
    public String saveRecipe(@ModelAttribute("recipe") RecipeEntity recipe, Principal principal) {
        String username = principal.getName();
        UserEntity user = userRepository.findByUsername(username);
        recipe.setUser(user);
        recipeRepository.save(recipe);
        return "redirect:/profile";
    }


    @PostMapping("/update-recipe/{id}")
    public String updateRecipe(@PathVariable("id") Long id, @ModelAttribute("recipe") RecipeEntity updatedRecipe, Principal principal) {
        Optional<RecipeEntity> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            RecipeEntity existingRecipe = optionalRecipe.get();

            // Behåll användaren från det befintliga receptet
            updatedRecipe.setUser(existingRecipe.getUser());

            // Sätt ID på det uppdaterade receptet
            updatedRecipe.setId(id);

            // Spara det uppdaterade receptet
            recipeRepository.save(updatedRecipe);

            return "redirect:/profile";
        } else {
            return "error";
        }
    }


    @PostMapping("/delete-recipe")
    public String deleteRecipe(@ModelAttribute("recipeId") Long recipeId) {
        recipeRepository.deleteById(recipeId);
        return "redirect:/profile";
    }


    @GetMapping("/recipe/{id}")
    public String showRecipeDetails(@PathVariable("id") Long id, Model model) {
        Optional<RecipeEntity> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            RecipeEntity recipe = optionalRecipe.get();
            model.addAttribute("recipe", recipe);
            return "recipe-details";
        } else {
            return "error";
        }
    }

    @GetMapping("/all-recipes")
    public String showAllRecipes(Principal principal, Model model) {
        String username = principal.getName();
        UserEntity user = userRepository.findByUsername(username);

        if (user != null) {
            List<RecipeEntity> userRecipes = user.getRecipes();
            if (userRecipes != null) {
                model.addAttribute("recipes", userRecipes);
            } else {
                model.addAttribute("errorMessage", "Inga recept hittades för användaren.");
            }
        } else {
            model.addAttribute("errorMessage", "Användaren kunde inte hittas.");
        }

        return "all-recipes";
    }




    @GetMapping("/search-recipe")
    public String searchRecipes(@RequestParam("keyword") String keyword, Principal principal, Model model) {
        // Hämta inloggad användares användarnamn
        String username = principal.getName();

        // Hämta den inloggade användaren från UserRepository
        UserEntity user = userRepository.findByUsername(username);

        if (user != null) {
            // Sök efter recept som matchar sökordet och tillhör den inloggade användaren
            List<RecipeEntity> userRecipes = recipeRepository.findByTitleContaining(keyword);

            // Lägg till recepten i modellen för att visas på sidan
            model.addAttribute("recipes", userRecipes);
            model.addAttribute("keyword", keyword); // Lägg även till sökordet i modellen för att visa det på sidan
        }

        return "search-results"; // Returnera sidan för sökresultat
    }



}






