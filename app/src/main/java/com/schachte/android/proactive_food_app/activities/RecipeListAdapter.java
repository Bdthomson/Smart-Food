package com.schachte.android.proactive_food_app.activities;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.models.Recipe;
import com.squareup.picasso.Picasso;

/**
 * Created by Spencer Smith on 3/28/2017.
 *
 * This adapter will be used by the recipe list activity to show all of our sweet recipes in a nice way
 */

public class RecipeListAdapter extends ArrayAdapter<Recipe> {

    public RecipeListAdapter(Context context, List<Recipe> recipes) {
        super(context, 0, recipes); //Boolean value signifies requery which will requery the data each time it chagnes
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Recipe recipe = getItem(position);
        if( convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_cell, parent, false);
        }

        TextView mainText = (TextView) convertView.findViewById(R.id.firstLine);
        TextView secondTextView = (TextView) convertView.findViewById(R.id.secondLine);
        TextView thirdTextView = (TextView) convertView.findViewById(R.id.thirdLine);

        ImageView recipeImage = (ImageView) convertView.findViewById(R.id.recipeImage);

        mainText.setText(recipe.getRecipeName());
        secondTextView.setText("Calories: " + recipe.getCalories());
        thirdTextView.setText("Ready in: " + recipe.getReadyInMinutes() + " minutes.");

        Picasso.with(convertView.getContext()).load(recipe.getImageUrl()).resize(200,200).centerCrop().into(recipeImage);
        //recipeImage.setImageBitmap(bitmap);


        return convertView;
    }
}
