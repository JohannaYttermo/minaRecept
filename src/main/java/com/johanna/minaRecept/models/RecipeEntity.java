package com.johanna.minaRecept.models;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "recipes")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotEmpty(message = "Denna ruta får inte vara tom")
    private String title;
    @NotEmpty(message = "Lägg till ingredienser")
    private String ingredients;

    public RecipeEntity() {}

    public RecipeEntity(String title, String ingredients) {
        this.title = title;
        this.ingredients = ingredients;
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
