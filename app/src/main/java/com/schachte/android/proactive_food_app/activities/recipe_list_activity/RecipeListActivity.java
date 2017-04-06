package com.schachte.android.proactive_food_app.activities.recipe_list_activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.database.DataAccessLayer;
import com.schachte.android.proactive_food_app.models.PantryData;
import com.schachte.android.proactive_food_app.models.Recipe;
import com.schachte.android.proactive_food_app.util.WebServices;

import java.util.ArrayList;
import java.util.List;


public class RecipeListActivity extends AppCompatActivity {

    private ListView recipeListView;
    private RecipeListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        //Get the recipes from the database
        DataAccessLayer dal = new DataAccessLayer(this);
        //List<Recipe> currentRecipes = dal.getRecipes();

        PantryData pantryData = new PantryData();
        pantryData.setMealPreference("Dinner");
        pantryData.setActivityLevel("Low");
        List<String> userIngred = new ArrayList<>();
        userIngred.add("chocolate");
        pantryData.setUserIngredients(userIngred);

        WebServices ws = new WebServices();
        List<Recipe> recipeList = ws.getRecipesFromServer(pantryData);

        //Create the adapter using the recipe list
        adapter = new RecipeListAdapter(this, recipeList);

        //Get the ListView object and assign the adapter to it
        recipeListView = (ListView) this.findViewById(R.id.recipeListView);
        recipeListView.setAdapter(adapter);


    }
}