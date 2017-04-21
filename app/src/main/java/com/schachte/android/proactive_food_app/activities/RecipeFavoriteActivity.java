package com.schachte.android.proactive_food_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.activities.recipe_detail_activity.RecipeDetailActivity;
import com.schachte.android.proactive_food_app.activities.recipe_list_activity.RecipeListActivity;
import com.schachte.android.proactive_food_app.database.DataAccessLayer;
import com.schachte.android.proactive_food_app.database.SqlQueries;
import com.schachte.android.proactive_food_app.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeFavoriteActivity extends AppCompatActivity {

    private ListView recipeListView;
    private RecipeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_favorite);

        //Get all of the favorited recipes from the database
        final DataAccessLayer dal = new DataAccessLayer(this);
        final List<Recipe> recipeList = dal.getRecipes(SqlQueries.SELECT_FAVORITE_RECIPES);

        //Create an adapter using the recipes and set it as an adapter
        adapter = new RecipeListAdapter(this, recipeList);
        recipeListView = (ListView) findViewById(R.id.recipeListView);
        recipeListView.setAdapter(adapter);

        //Set the on item listener to go to the recipe detail page
        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toRecipeDetail = new Intent(RecipeFavoriteActivity.this, RecipeDetailActivity.class);
                toRecipeDetail.putExtra( RecipeDetailActivity.RECIPE_STRING, adapter.getItem(position) );
                startActivity(toRecipeDetail);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        DataAccessLayer dal = new DataAccessLayer(this);
        ArrayList<Recipe> recipeList = dal.getRecipes(SqlQueries.SELECT_FAVORITE_RECIPES);
        if( recipeList.size() > 0 )
            setAdapterList(recipeList);
    }

    /*
     * Will set the ArrayListAdapter to use the recipeList data
     */
    private void setAdapterList(ArrayList<Recipe> recipeList) {
        //Create the adapter using the recipe list
        adapter = new RecipeListAdapter(this, recipeList);

        //Get the ListView object and assign the adapter to it
        recipeListView.setAdapter(adapter);
    }
}
