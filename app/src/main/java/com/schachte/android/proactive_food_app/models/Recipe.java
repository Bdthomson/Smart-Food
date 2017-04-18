package com.schachte.android.proactive_food_app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable {

    private List<String> allIngredients;

    private String recipeName;
    private String imageUrl;
    private String imageByteData;
    private String sourceUrl;
    private String proteinCount;
    private String fatCount;
    private String carbCount;
    private String isFavorite;

    private int readyInMinutes;
    private int recipeId;
    private int servings;
    private int calories;


    public Recipe() {
        allIngredients = new ArrayList<>();
    }

    public List<String> getAllIngredients() {
        return allIngredients;
    }
    public void setAllIngredients(List<String> allIngredients) {
        this.allIngredients = allIngredients;
    }
    public void addIngredient(String newIngredient) {
        this.allIngredients.add(newIngredient);
    }
    public String getRecipeName() {
        return recipeName;
    }
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageByteData() {
        return imageByteData;
    }
    public void setImageByteData(String imageByteData) {
        this.imageByteData = imageByteData;
    }
    public String getSourceUrl() {
        return sourceUrl;
    }
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
    public String getProteinCount() {
        return proteinCount;
    }
    public void setProteinCount(String proteinCount) {
        this.proteinCount = proteinCount;
    }
    public String getFatCount() {
        return fatCount;
    }
    public void setFatCount(String fatCount) {
        this.fatCount = fatCount;
    }
    public String getCarbCount() {
        return carbCount;
    }
    public void setIsFavorite(String isFavorite) {this.isFavorite = isFavorite;}
    public String getIsFavorite() { return isFavorite; }
    public void setCarbCount(String carbCount) {
        this.carbCount = carbCount;
    }
    public int getReadyInMinutes() {
        return readyInMinutes;
    }
    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }
    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
    public int getServings() {
        return servings;
    }
    public void setServings(int servings) {
        this.servings = servings;
    }
    public int getCalories() {
        return calories;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }

}
