package com.schachte.android.proactive_food_app.models;

import java.io.Serializable;

public class Ingredient implements Serializable {

    private String ingredientName;
    private String ingredientGeneralName;
    private String ingredientImageURL;
    private String ingredientImageBytes;
    private int ingredientId;

    public Ingredient() {

    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientGeneralName() {
        return ingredientGeneralName;
    }

    public void setIngredientGeneralName(String ingredientGeneralName) {
        this.ingredientGeneralName = ingredientGeneralName;
    }

    public String getIngredientImageURL() {
        return ingredientImageURL;
    }

    public void setIngredientImageURL(String ingredientImageURL) {
        this.ingredientImageURL = ingredientImageURL;
    }

    public String getIngredientImageBytes() {
        return ingredientImageBytes;
    }

    public void setIngredientImageBytes(String ingredientImageBytes) {
        this.ingredientImageBytes = ingredientImageBytes;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }



}
