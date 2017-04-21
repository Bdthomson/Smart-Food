package com.schachte.android.proactive_food_app.activities.recipe_detail_activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.activities.WebActivity;
import com.schachte.android.proactive_food_app.database.DataAccessLayer;
import com.schachte.android.proactive_food_app.database.SqlQueries;
import com.schachte.android.proactive_food_app.models.Recipe;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.Arrays;

public class RecipeDetailActivity extends AppCompatActivity {

    public static String RECIPE_STRING = "selected_recipe";
    private static Recipe recipe;
    private ImageButton favoriteButton;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intentFromList = getIntent();

        if( savedInstanceState != null ) {
            Serializable recipeObject = savedInstanceState.getSerializable(RECIPE_STRING);
            if (recipeObject != null)
                recipe = (Recipe) recipeObject;
        } else if( intentFromList != null ) {
            Serializable recipeObject = intentFromList.getSerializableExtra(RECIPE_STRING);
            if (recipeObject != null)
                recipe = (Recipe) recipeObject;
        }

        if( getSupportActionBar() != null ) {
            getSupportActionBar().setTitle(recipe.getRecipeName());
        }
        /*
         * Set the recipe image
         */
        ImageView recipeImage = (ImageView) findViewById(R.id.recipeImage);
        Picasso.with(this).load(recipe.getImageUrl()).resize(250,250).centerCrop().into(recipeImage);

        /*
         * Functionality for the "favorite" button
         */
        favoriteButton = (ImageButton) findViewById(R.id.favoriteButton);

        //If it has already been favorited, set to gold, else set grey
        if( "Y".equals(recipe.getIsFavorite() ) ) {
            Drawable starImage = getResources().getDrawable(android.R.drawable.btn_star_big_on, null);
            if(starImage != null)
                favoriteButton.setImageDrawable(starImage);
        } else {
            Drawable starImage = getResources().getDrawable(android.R.drawable.btn_star_big_off, null);
            if(starImage != null)
                favoriteButton.setImageDrawable(starImage);
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
            }
        });


        /*
         * Ready in/Servings/Nutrition Facts
         */
        TextView readyInText = (TextView) findViewById(R.id.readyInText);
        TextView servingsText = (TextView) findViewById(R.id.servingsText);
        TextView calorieText = (TextView) findViewById(R.id.calorieCount);
        TextView carbText = (TextView) findViewById(R.id.carbCount);
        TextView fatText = (TextView) findViewById(R.id.fatCount);
        TextView proteinText = (TextView) findViewById(R.id.proteinCount);

        readyInText.setText("Ready in: " + recipe.getReadyInMinutes() + " minutes.");
        servingsText.setText("Servings: " + recipe.getServings() + "");
        calorieText.setText("Calories: " + recipe.getCalories() + "");

        if( recipe.getCarbCount() != null ) carbText.setText( "Carbs: " + recipe.getCarbCount() + "");
        if( recipe.getFatCount() != null ) fatText.setText( "Fat: " + recipe.getFatCount() + "");
        if( recipe.getProteinCount() != null ) proteinText.setText( "Protein: " + recipe.getProteinCount() + "");

        /*
         * Set the ingredient list at the bottom of the page.  This used a RecyclerView because
         * a ListView inside of a NestedScrollView does not correctly inflate.
         */
        IngredientListAdapter adapter = new IngredientListAdapter( recipe.getAllIngredients() );
        RecyclerView listView = (RecyclerView)findViewById(R.id.ingredientList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);

        /*
         * Set up the "See Full Recipe" button
         */
        webView = (WebView) findViewById(R.id.webView);
        Button seeFullRecipe = (Button) findViewById(R.id.fullRecipeButton);
        seeFullRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toWebActivity = new Intent(RecipeDetailActivity.this, WebActivity.class);
                toWebActivity.putExtra(SqlQueries.RECIPE_SOURCE_URL, recipe.getSourceUrl());
                startActivity(toWebActivity);
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(RECIPE_STRING, recipe);
        super.onSaveInstanceState(outState);
    }

    /*
     * If the recipe is favorited, it will unfavorite it and change the star back to grey, if
     * is isn't favorited it will set favorite="Y" in the db and change the star to gold
     */
    private void toggleFavorite() {
        DataAccessLayer dal = new DataAccessLayer(this);

        if( "Y".equals( recipe.getIsFavorite()) ) {
            Toast.makeText(this, "Recipe removed from favorites", Toast.LENGTH_SHORT).show();
            dal.updateRecipeFavorite(recipe.getRecipeId(), "N");
            recipe.setIsFavorite("N");
            Drawable starImage = getResources().getDrawable(android.R.drawable.btn_star_big_off, null);
            favoriteButton.setImageDrawable(starImage);
        } else {
            Toast.makeText(this, "Recipe added to favorites", Toast.LENGTH_SHORT).show();
            dal.updateRecipeFavorite(recipe.getRecipeId(), "Y");
            recipe.setIsFavorite("Y");
            Drawable starImage = getResources().getDrawable(android.R.drawable.btn_star_big_on, null);
            favoriteButton.setImageDrawable(starImage);
        }

    }
}
