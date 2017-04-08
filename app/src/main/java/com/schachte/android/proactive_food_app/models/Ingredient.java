package com.schachte.android.proactive_food_app.models;

/**
 * Created by Ryan on 4/8/17.
 */

public class Ingredient {
    private String name;
    private String rawImageBlob;

    public Ingredient(String name) {
        this.name = name;
        this.rawImageBlob = null;
    }

    public Ingredient(String name, String blob) {
        this.name = name;
        this.rawImageBlob = blob;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlob() {
        return this.rawImageBlob;
    }

    public void setBlob(String blob) {
        this.rawImageBlob = blob;
    }

    @Override
    public String toString() {
        return "[Ingredient: " + this.name + "]";
    }
}
