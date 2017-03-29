package com.schachte.android.proactive_food_app.database;


import java.util.List;

/**
 * Created by Spencer Smith on 3/28/2017.
 *
 * An object of this class will contain all of the necessary information to view and
 * explore a recipe.
 */

public class Recipe {

    private String recipeName;
    private String recipeUrl;
    private List<String> ingredients;

    public Recipe(final String recipeName, final String recipeUrl, final List<String> ingredients) {
        this.recipeName = recipeName;
        this.recipeUrl = recipeUrl;
        this.ingredients = ingredients;
    }


    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeUrl() {
        return recipeUrl;
    }

    public void setRecipeUrl(String recipeUrl) {
        this.recipeUrl = recipeUrl;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeName='" + recipeName + '\'' +
                ", recipeUrl='" + recipeUrl + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
