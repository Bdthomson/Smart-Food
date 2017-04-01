package com.schachte.android.proactive_food_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.schachte.android.proactive_food_app.database.Preferences.getInstance;

public class Category extends Activity {

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

    /**
     * Converts a json object to a string arraylist to process cuisine values
     * @param JSON
     * @return
     */
    public ArrayList<String> jsonToList(String JSON){
        JSONArray array = null;
        ArrayList<String> strings = new ArrayList<>();
        try {
            array = new JSONArray(JSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (array != null) {
            // Loop through strings in the json array;
            for (int i = 0; i < array.length(); i++) {
                try {
                    strings.add(i, (String) array.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return strings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "C1 is " + getInstance().getPreferenceString("c1"));

        // Load Stringified JSON Array (default value is []) from shared preferences.
        ArrayList<String> checked;

        if (getInstance().getPreferenceString("c1") != null)
            checked = jsonToList(getInstance().getPreferenceString("c1"));
        else
            checked = new ArrayList<>();

        // Get GridView
        gridview = (GridView) findViewById(R.id.customgrid);

        // Create a custom adapter, setting all values in it's constructor.
        customAdapter = new CustomAdapter(this, osNameList, osImages, osImagesHover, checked);

        // Set the adapter for the gridView
        gridview.setAdapter(customAdapter);

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

            // Store the selected values.
            JSONArray array = new JSONArray();

            //Store any new values
            for (CategoryData data : selectedCategories) {
                array.put(data.catName);
            }

            //Write any newly selected or deselected values
            Log.d(TAG, "Logging data..");
            getInstance().writePreferenceString("c1", array.toString());

            if (!getInstance().getPreferenceBool("setupComplete")) {
                Intent intent = new Intent(Category.this, Preferences.class);
                startActivity(intent);
            } else {
                Log.d(TAG, "Um");
                finish();
            }
            }
        });
    }

}