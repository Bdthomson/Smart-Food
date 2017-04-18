package com.schachte.android.proactive_food_app.activities.ingredient_list_activity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.models.Ingredient;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Spencer Smith on 3/28/2017.
 *
 * This adapter will be used by the recipe list activity to show all of our sweet recipes in a nice way
 */

public class IngredientListAdapter extends ArrayAdapter<Ingredient> {

    public IngredientListAdapter(Context context, List<Ingredient> ingredients) {
        super(context, 0, ingredients); //Boolean value signifies requery which will requery the data each time it chagnes
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ingredient ingredient = getItem(position);
        if( convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_cell, parent, false);
        }

        TextView mainText = (TextView) convertView.findViewById(R.id.firstLine);
        TextView secondTextView = (TextView) convertView.findViewById(R.id.secondLine);
        TextView thirdTextView = (TextView) convertView.findViewById(R.id.thirdLine);

        ImageView recipeImage = (ImageView) convertView.findViewById(R.id.recipeImage);

        mainText.setText(ingredient.getIngredientName());
        secondTextView.setText("");
        thirdTextView.setText("");

        Picasso.with(convertView.getContext()).load(ingredient.getIngredientImageURL()).resize(200,200).centerCrop().into(recipeImage);

        return convertView;
    }
}
