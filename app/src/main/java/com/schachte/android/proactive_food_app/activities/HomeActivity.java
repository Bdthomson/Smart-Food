package com.schachte.android.proactive_food_app.activities;

/*
 * Future Feature Ideas:
 *  - Store all images in database after first load so that the app can completely work offline
 *  - Improve on the "generalized" food ingredient stuff
 *  - Ingredient page - able to take picture and add it
 *  - Take into account BMI/BMR in the calorie range to get recipes
 *  - Show what type of cuisine the recipe is
 *  - Show what meal the recipe is for
 */
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.activities.category_activity.CategoryActivity;
import com.schachte.android.proactive_food_app.activities.ingredient_list_activity.PantryActivity;
import com.schachte.android.proactive_food_app.activities.recipe_list_activity.RecipeListActivity;
import com.schachte.android.proactive_food_app.util.BackgroundHelper;
import com.schachte.android.proactive_food_app.util.PedometerStart;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import static com.schachte.android.proactive_food_app.database.Preferences.getInstance;

public class HomeActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    FloatingActionButton cuisineBtn;
    FloatingActionButton profileBtn;
    Button ingredientsBtn;
    Button pantryBtn;
    Button recipesBtn;
    Button favoritesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instantiate the singleton class for managing user-prefs onload
        getInstance().Initialize(getApplicationContext());

        //Initialize pedometer sensor here if the background service is not already registered
        BackgroundHelper utils = new BackgroundHelper(this);
        Boolean serviceRunning = utils.isMyServiceRunning(PedometerStart.class);

        if (!serviceRunning) {
            Intent serviceIntent = new Intent(this, PedometerStart.class);
            this.startService(serviceIntent);
        }

        //Load the categories screen if the setup is not yet complete
        if (!getInstance().getPreferenceBool("setupComplete")) {
            Intent go = new Intent(this, CategoryActivity.class);
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
        recipesBtn = (Button) findViewById(R.id.make_me_food_btn);
        favoritesBtn = (Button) findViewById(R.id.view_favorites);

        cuisineBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            // Send to next activity
            startActivity(new Intent(HomeActivity.this, CategoryActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            // Send to next activity
            startActivity(new Intent(HomeActivity.this, PreferencesActivity.class));
            }
        });

        ingredientsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            // Send to next activity
            startActivity(new Intent(HomeActivity.this, AddIngredientsActivity.class));
            }
        });

        pantryBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send to next activity
                startActivity(new Intent(HomeActivity.this, PantryActivity.class));
            }
        });

        recipesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RecipeListActivity.class));
            }
        });

        favoritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RecipeFavoriteActivity.class));
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
