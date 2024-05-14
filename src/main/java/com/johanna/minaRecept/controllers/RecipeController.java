package com.johanna.minaRecept.controllers;

import com.johanna.minaRecept.repositories.RecipeRepository;
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

    @Autowired
    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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
    public String saveRecipe(@ModelAttribute("recipe") RecipeEntity recipe) {
        recipeRepository.save(recipe);
        return "redirect:/profile";
    }

    @PostMapping("/update-recipe/{id}")
    public String updateRecipe(@PathVariable("id") Long id, @ModelAttribute("recipe") RecipeEntity updatedRecipe) {
        updatedRecipe.setId(id);
        recipeRepository.save(updatedRecipe);
        return "redirect:/profile";
    }

    @PostMapping("/delete-recipe")
    public String deleteRecipe(@ModelAttribute("recipeId") Long recipeId) {
        recipeRepository.deleteById(recipeId);
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username",username);
        List<RecipeEntity> recipes = recipeRepository.findAll();
        model.addAttribute("recipes", recipes);
        return "profile";
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
}





