package com.johanna.minaRecept.models;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Entity
@Table(name = "recipes")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Denna ruta får inte vara tom")
    private String title;

    @NotEmpty(message = "Lägg till ingredienser")
    @ElementCollection
    private List<String> ingredients;

    @NotEmpty
    private String instructions;



    public RecipeEntity() {}

    public RecipeEntity(String title, List<String> ingredients, String instructions) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public @NotEmpty(message = "Lägg till ingredienser") List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
