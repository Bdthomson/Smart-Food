package com.schachte.android.proactive_food_app.activities.category_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.schachte.android.proactive_food_app.activities.PreferencesActivity;
import com.schachte.android.proactive_food_app.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.schachte.android.proactive_food_app.database.Preferences.getInstance;

public class CategoryActivity extends Activity {

    GridView gridview;

    ArrayList<CategoryData> selectedCategories;

    public CuisineAdapter cuisineAdapter;

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

        // Load the checked categories from shared preferences.
        Set<String> checked = getInstance().getPreferenceStringSet("categories");
        Log.d(TAG, "Categories are " + checked);

        // Get GridView
        gridview = (GridView) findViewById(R.id.customgrid);

        // Create a custom adapter, setting all values in it's constructor.
        cuisineAdapter = new CuisineAdapter(this, osNameList, osImages, osImagesHover, checked);

        // Set the adapter for the gridView
        gridview.setAdapter(cuisineAdapter);

        // Button Listeners
        registerButtonListeners();

    }

    private void registerButtonListeners() {

        // The save button loads the selected choices from UI and saves them to shared preferences.
        saveButton = (Button) findViewById(R.id.save_categories);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Get selected categories.
                selectedCategories = cuisineAdapter.getSelectedCategories();

                // Store the selected values in a string set.
                HashSet<String> tempSet = new HashSet<>();

                //Store any new values
                for (CategoryData data : selectedCategories) {
                    tempSet.add(data.catName);
                }

                //Write any newly selected or deselected values
                getInstance().writePreferenceStringSet("categories", tempSet);

                if (!getInstance().getPreferenceBool("setupComplete")) {
                    Intent intent = new Intent(CategoryActivity.this, PreferencesActivity.class);
                    startActivity(intent);
                } else {
                    finish();
                }
            }
        });
    }

}