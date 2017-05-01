package com.schachte.android.proactive_food_app.activities.ingredient_list_activity;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.models.Ingredient;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URL;
import java.util.List;

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

        ImageView ingredientImage = (ImageView) convertView.findViewById(R.id.ingredientImage);

        mainText.setText(ingredient.getIngredientName());
        secondTextView.setText(ingredient.getIngredientGeneralName());
        thirdTextView.setText("");

        //TODO: Modify this to load from file system sometime
        Picasso.with(convertView.getContext()).cancelRequest(ingredientImage);

        final String ingredientImageURL = ingredient.getIngredientImageURL();

        if( ingredientImageURL != null && !ingredientImageURL.equals("none") )
            //ingredientImage.setImageResource(R.mipmap.am);
            Picasso.with(convertView.getContext()).load(ingredientImageURL).resize(200,200).centerCrop().into(ingredientImage);
        else {
            ingredientImage.setImageResource(R.mipmap.food);
        }

//        if (URLorFile != null) {
//            Log.i(URLorFile, URLorFile);
//            File f = new File(URLorFile);
//            Log.i("test", String.valueOf(f.exists()));
//            if ((f.exists() && !f.isDirectory()) ){
//                Log.i("yes", "yes");
//                Picasso.with(convertView.getContext()).load(f).resize(200,200).centerCrop().into(recipeImage);
//            } else if (isValidURL(URLorFile)) {
//                Picasso.with(convertView.getContext()).load(ingredient.getIngredientImageURL()).resize(200,200).centerCrop().into(recipeImage);
//            }
//        }

        return convertView;
    }

    private boolean isValidURL(String urLorFile) {
        try {
            URL url = new URL(urLorFile);
            url.toURI();
            return true;
        } catch (Exception exception)
        {
            return false;
        }
    }
}
