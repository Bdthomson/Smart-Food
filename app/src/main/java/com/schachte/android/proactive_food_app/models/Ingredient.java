package com.schachte.android.proactive_food_app.models;

import android.widget.ImageView;

/**
 * Created by Ryan on 4/8/17.
 */

public class Ingredient {
    private String name;
    private String rawImageBlob;
    private ImageView imageView;

    public Ingredient(String name) {
        this.name = name;
        this.rawImageBlob = null;
        this.imageView = null;
    }

    public Ingredient(String name, String blob) {
        this.name = name;
        this.rawImageBlob = blob;
        decodeBase64ImageAndStore();
    }

    private void decodeBase64ImageAndStore() {
//        byte[] decodedBytes = android.util.Base64.decode(this.rawImageBlob, 0);
//        this.imageView.setImageBitmap(BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length));
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public String getBlob() {
        return this.rawImageBlob;
    }

    public void setBlob(String blob) {
        this.rawImageBlob = blob;
        decodeBase64ImageAndStore();
    }

    @Override
    public String toString() {
        return "[Ingredient: " + this.name + ", imageBlob: " + this.rawImageBlob + "]";
    }
}
