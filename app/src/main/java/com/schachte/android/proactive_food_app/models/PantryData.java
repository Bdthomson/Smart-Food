package com.schachte.android.proactive_food_app.models;

import java.util.ArrayList;
import java.util.List;

public class PantryData {

    public List<Ingredient> getUserIngredients() {
        return userIngredients;
    }

    public void setUserIngredients(List<Ingredient> userIngredients) {
        this.userIngredients = userIngredients;
    }

    private List<Ingredient> userIngredients = new ArrayList<>();


}
