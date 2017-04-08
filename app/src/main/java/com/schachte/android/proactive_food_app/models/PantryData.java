package com.schachte.android.proactive_food_app.models;

import java.util.ArrayList;
import java.util.List;

public class PantryData {

    private List<Ingredient> userIngredients = new ArrayList<>();
    private List<String> cuisinePreferences = new ArrayList<>();
    private String mealPreference;			//Breakfast, Lunch, Dinner
    private String activityLevel; 			//High, Medium, Low

    public List<Ingredient> getUserIngredients() {
        return userIngredients;
    }

    public void setUserIngredients(List<String> userIngredients) {
        userIngredients.clear();
        for (String ingredient : userIngredients) {
            Ingredient i = new Ingredient(ingredient);
            this.userIngredients.add(i);
        }
    }

    public void addUserIngredient(Ingredient userIngredient) {
        this.userIngredients.add(userIngredient);
    }
    public void addUserIngredients(List<Ingredient> userIngredients) {
        this.userIngredients.addAll(userIngredients);
    }
    public List<String> getCuisinePreferences() {
        return cuisinePreferences;
    }
    public void setCuisinePreferences(List<String> cuisinePreferences) {
        this.cuisinePreferences = cuisinePreferences;
    }
    public String getMealPreference() {
        return mealPreference;
    }
    public void setMealPreference(String mealPreference) {
        this.mealPreference = mealPreference;
    }
    public String getActivityLevel() {
        return activityLevel;
    }
    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }
    @Override
    public String toString() {
        return "PantryData [userIngredients=" + userIngredients + ", cuisinePreferences=" + cuisinePreferences
                + ", mealPreference=" + mealPreference + ", activityLevel=" + activityLevel + "]";
    }


}
