package com.schachte.android.proactive_food_app;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import static com.schachte.android.proactive_food_app.database.Preferences.getInstance;

import com.schachte.android.proactive_food_app.activities.recipe_list_activity.RecipeListActivity;
import com.schachte.android.proactive_food_app.database.Preferences.*;
import com.schachte.android.proactive_food_app.util.WebServices;

public class Home extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    FloatingActionButton cuisineBtn;
    FloatingActionButton profileBtn;
    Button ingredientsBtn;
    Button pantryBtn;
    Button recipesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instantiate the singleton class for managing user-prefs onload
        getInstance().Initialize(getApplicationContext());

        //Load the categories screen if the setup is not yet complete
        if (!getInstance().getPreferenceBool("setupComplete")) {
            Intent go = new Intent(this, Category.class);
            finish();
            startActivity(go);
        } else {
            setContentView(R.layout.activity_home);
            setFabMenu();
            registerButtonListeners();
        }
    }

    /**
     * Adding click functionality to fab buttons and menu choices
     */
    private void registerButtonListeners() {

        // The save button loads the selected choices from UI and saves them to shared preferences.
        cuisineBtn = (FloatingActionButton) findViewById(R.id.fab_cuisine);
        profileBtn = (FloatingActionButton) findViewById(R.id.fab_profile);
        ingredientsBtn = (Button) findViewById(R.id.ingredients_button);
        pantryBtn = (Button) findViewById(R.id.view_pantry_btn_home);
        recipesBtn = (Button) findViewById(R.id.add_ingredients_btn);

        cuisineBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            // Send to next activity
            startActivity(new Intent(Home.this, Category.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            // Send to next activity
            startActivity(new Intent(Home.this, Preferences.class));
            }
        });

        ingredientsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            // Send to next activity
            startActivity(new Intent(Home.this, Ingredients.class));
            }
        });

        pantryBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send to next activity
                startActivity(new Intent(Home.this, Pantry.class));
            }
        });

        recipesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, RecipeListActivity.class));
            }
        });
    }

    /**
     * Colorizes buttons and adds proper methods to each button
     */
    private void setFabMenu(){

        int defaultFabButtonColor = Color.WHITE;

        //Get all the buttons
        FloatingActionMenu mainButton = (FloatingActionMenu) findViewById(R.id.fab_cog);

        //Colorize to defaultFabButtonColor
        mainButton.getMenuIconView().setColorFilter(defaultFabButtonColor);
        mainButton.getMenuIconView().setImageResource(R.drawable.fabtn_cog);

    }
}
