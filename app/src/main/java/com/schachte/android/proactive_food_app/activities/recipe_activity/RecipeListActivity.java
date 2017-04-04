package com.schachte.android.proactive_food_app.activities.recipe_activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.database.DataAccessLayer;
import com.schachte.android.proactive_food_app.models.Recipe;

import java.util.ArrayList;
import java.util.List;


public class RecipeListActivity extends AppCompatActivity {

    private ListView recipeList;
    private RecipeListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        //Get the recipes from the database
        DataAccessLayer dal = new DataAccessLayer(this);
        //List<Recipe> currentRecipes = dal.getRecipes();

        List<Recipe> currentRecipes = new ArrayList<Recipe>();
        currentRecipes.add(new Recipe("TEST", "TEST", null));
        currentRecipes.add(new Recipe("TEST", "TEST", null));
        currentRecipes.add(new Recipe("TEST", "TEST", null));
        currentRecipes.add(new Recipe("TEST", "TEST", null));

        //Create the adapter using the recipe list
        adapter = new RecipeListAdapter(this, currentRecipes);

        //Get the ListView object and assign the adapter to it
        recipeList = (ListView) this.findViewById(R.id.recipeListView);
        recipeList.setAdapter(adapter);


    }
}