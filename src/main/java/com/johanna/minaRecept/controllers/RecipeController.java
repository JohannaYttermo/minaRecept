package com.johanna.minaRecept.controllers;

import com.johanna.minaRecept.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.johanna.minaRecept.models.RecipeEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
/*@RequestMapping("/recipes")*/
public class RecipeController {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    @GetMapping("/add-recipe")
    public String showAddRecipeForm() {

        System.out.println("visar sidan att lägga till recept");
        return "add-recipe";
    }

    @PostMapping("/add-recipe")
    public String saveRecipe(@RequestParam String title, @RequestParam String ingredients) {
        RecipeEntity recipe;
        recipe = new RecipeEntity(title, ingredients);
        recipeRepository.save(recipe);
        System.out.println("recept sparas i databas");
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        List<RecipeEntity> recipes = recipeRepository.findAll();
        model.addAttribute("recipes", recipes);
        return "profile";
    }
}








/*
@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/add")
    public String showAddRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeEntity());
        return "add-recipe";
    }

    @PostMapping("/save")
    public String saveRecipe(@ModelAttribute RecipeEntity recipe) {
        recipeService.saveRecipe(recipe);
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        List<RecipeEntity> recipes = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipes);
        return "profile";
    }
}


*/