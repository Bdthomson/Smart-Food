package com.schachte.android.proactive_food_app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends Activity {

    GridView gridview;

    ArrayList<CategoryData> selectedCategories;

    public CustomAdapter customAdapter;

    public Button saveButton;

    public final String TAG = this.getClass().getSimpleName();


    //The number of food categories need to match with the images that are saved in the code below
    public static String[] osNameList = {
            "American Food",
            "Caribbean Food",
            "Chinese Food",
            "French Food",
            "German Food",
            "Greek Food",
            "Indian Food",
            "Italian Food",
            "Japanese Food",
            "Mexican Food",
            "Thai Food"

    };
    public static int[] osImages = {
            R.mipmap.am,
            R.mipmap.caribbean,
            R.mipmap.ch,
            R.mipmap.french,
            R.mipmap.german,
            R.mipmap.greece,
            R.mipmap.indian,
            R.mipmap.italian,
            R.mipmap.japenese,
            R.mipmap.mexican,
            R.mipmap.thai

    };

    public static int[] osImagesHover = {
            R.mipmap.am_hover,
            R.mipmap.caribbean_hover,
            R.mipmap.ch_hover,
            R.mipmap.french_hover,
            R.mipmap.german_hover,
            R.mipmap.greece_hover,
            R.mipmap.indian_hover,
            R.mipmap.italian_hover,
            R.mipmap.japense_hover,
            R.mipmap.mexican_hover,
            R.mipmap.thai_hover

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get GridView
        gridview = (GridView) findViewById(R.id.customgrid);

        // Create a custom adapter, setting all values in it's constructor.
        customAdapter = new CustomAdapter(this, osNameList, osImages, osImagesHover);

        // Set the adapter for the gridView
        gridview.setAdapter(customAdapter);

        // Restore preferences on activity load.
        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);

        // Load Stringified JSON Array (default value is []) from shared preferences.
        String checkedArray = settings.getString("c1", "[]");

        // Set selected values for categories.
        customAdapter.setChecked(checkedArray);

        // Button Listeners
        registerButtonListeners();

    }

    private void registerButtonListeners() {

        // The save button loads the selected choices from UI and saves them to shared preferences.
        saveButton = (Button) findViewById(R.id.save_categories);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Get selected categories.
                selectedCategories = customAdapter.getSelectedCategories();

                // // Print them out.
                for (CategoryData data : selectedCategories) {
                    Log.d(TAG, data.catName);
                }

                // We need an Editor object to make preference changes.
                // All objects are from android.context.Context
                SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();


                // Store the selected values.
                JSONArray array = new JSONArray();

                // // Print them out.
                for (CategoryData data : selectedCategories) {
                    array.put(data.catName);
                }

                Log.d(TAG, array.toString());
                editor.putString("c1", array.toString());


                // Commit the changes.
                editor.commit();

            }
        });
    }

}