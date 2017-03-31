package com.schachte.android.proactive_food_app;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import static com.schachte.android.proactive_food_app.database.Preferences.getInstance;
import com.schachte.android.proactive_food_app.database.Preferences.*;

public class Home extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    FloatingActionButton cuisineBtn;
    FloatingActionButton profileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setFabMenu();
        registerButtonListeners();

        //Instantiate the singleton class for managing user-prefs onload
        getInstance().Initialize(getApplicationContext());

    }

    /**
     * Adding click functionality to fab buttons and menu choices
     */
    private void registerButtonListeners() {

        // The save button loads the selected choices from UI and saves them to shared preferences.
        cuisineBtn = (FloatingActionButton) findViewById(R.id.fab_cuisine);
        profileBtn = (FloatingActionButton) findViewById(R.id.fab_profile);

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
