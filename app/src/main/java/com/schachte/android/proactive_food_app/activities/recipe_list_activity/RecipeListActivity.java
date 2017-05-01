package com.schachte.android.proactive_food_app.activities.recipe_list_activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.activities.RecipeListAdapter;
import com.schachte.android.proactive_food_app.activities.recipe_detail_activity.RecipeDetailActivity;
import com.schachte.android.proactive_food_app.database.DataAccessLayer;
import com.schachte.android.proactive_food_app.database.Preferences;
import com.schachte.android.proactive_food_app.database.SqlQueries;
import com.schachte.android.proactive_food_app.models.PantryData;
import com.schachte.android.proactive_food_app.models.Recipe;
import com.schachte.android.proactive_food_app.util.WebServices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;


public class RecipeListActivity extends AppCompatActivity implements MealDialog.MealDialogListener, WebServices.WebServiceResponseListener {

    private ListView recipeListView;
    private RecipeListAdapter adapter;

    private boolean alreadySetMeal = false;
    private MealDialog.Meal mealCurrentlySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recipeListView = (ListView) this.findViewById(R.id.recipeListView);

        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toRecipeDetail = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
                toRecipeDetail.putExtra( RecipeDetailActivity.RECIPE_STRING, adapter.getItem(position) );
                startActivity(toRecipeDetail);
            }
        });
    }

    /*
     * Using onResume because it is called later than onCreate (more likely that the activity
     * will be visible.  Also this will be called if the app goes into background then
     * returns
     */
    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Recipe> recipeList = getRecipes();
        if( recipeList.size() > 0 )
            setAdapterList(recipeList);
    }

    /*
     * Sets the action bar to have the items (cog and refresh icon)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_recipe_bar, menu);
        return true;
    }

    /*
     * Set what happens when the items on the top action bar are selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                MealDialog settings = new MealDialog();

                if(!alreadySetMeal)
                    determineMealToSearch();

                settings.setMealToDefault(mealCurrentlySelected);
                settings.show(getSupportFragmentManager(), "meal");
                alreadySetMeal = true;
                return true;

            case R.id.action_refresh:
                DataAccessLayer dal = new DataAccessLayer(this);
                dal.deleteStoredRecipes();
                dal.close();
                adapter.clear();
                getRecipes();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     * Used when the settings dialog is opened, if it is the first time we open it
     * we will determine what the next meal is and default the value to that.  If the user
     * doesn't choose anything, the recipes that are shown will be for the next meal
     * based on the time of the clock on the device.
     */
    private void determineMealToSearch() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if( hour < 9 || hour > 21 )
            mealCurrentlySelected = MealDialog.Meal.Breakfast;
        else if( hour < 14 )
            mealCurrentlySelected = MealDialog.Meal.Lunch;
        else
            mealCurrentlySelected = MealDialog.Meal.Dinner;
    }

    /*
     * If no recipes are found in the database, we will pull them from the webservice.
     */
    private ArrayList<Recipe> getRecipes() {
        DataAccessLayer dal = new DataAccessLayer(this);
        ArrayList<Recipe> recipeList = dal.getRecipes(SqlQueries.SELECT_NEW_RECIPES);

        if(recipeList.size() == 0) {
            getRecipesFromServer(dal);
        } else {
            setAdapterList(recipeList);
        }

        return recipeList;
    }

    /*
     * Creates the PantryData object which will hold all of the current context
     * values that we know and send them to the WebServices class to be run async
     */
    private void getRecipesFromServer(DataAccessLayer dal) {
        PantryData pantryData = new PantryData();

        pantryData.setUserIngredients( dal.getIngredientsString() );
        Set<String> preferredCuisines = Preferences.getInstance().getPreferenceStringSet("categories");
        List<String> cuisineList = new ArrayList<>(preferredCuisines);
        pantryData.setCuisinePreferences(cuisineList);

        /*
         * Get the step counts for today and our average up to this minute of the day for the
         * past two weeks.  If I've walked less than I normally do at this time of the day
         * I will lower the calorie of the recipes that are returned slightly to a 2000 calorie diet.
         * If I've walked more the calorie restrictions will be based on a 2400 calorie diet
         */
        int stepCount = dal.getDailyStepCount(); // Steps logged today.
        int averageStepCount = dal.getAverageForNow(); // Average steps logged by current local time.
        if(stepCount < averageStepCount)
            pantryData.setActivityLevel("Low");
        else
            pantryData.setActivityLevel("High");

        //If the user hasn't selected any
        if( mealCurrentlySelected == null )
            determineMealToSearch();
        pantryData.setMealPreference( mealCurrentlySelected.name() );

        //Close to avoid locking issues
        dal.close();

        //Get the recipes from the web service
        WebServices ws = new WebServices();
        ws.getRecipesFromServer(pantryData, RecipeListActivity.this);
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
    /*
     * Called from the MealDialog whenever the user chooses a different option.
     */
    @Override
    public void onChangeMeal(MealDialog.Meal mealToSearch) {
        mealCurrentlySelected = mealToSearch;
    }

    /*
     * This method is overloaded from the WebServices.WebServiceResponseListener interface
     * and will be called when all of the data has returned from the webservice as to not
     * block the UI thread.  When this is called the adapter will be set and the data
     * will be stored in the database.
     */
    @Override
    public void onTaskCompleted(Object returnObject) {

        if( returnObject instanceof Recipe[] ) {
            Recipe[] recipeArray = (Recipe[]) returnObject;
            ArrayList<Recipe> recipeList = new ArrayList<Recipe>( Arrays.asList(recipeArray) );

            setAdapterList(recipeList);

            DataAccessLayer dal = new DataAccessLayer(this);
            dal.storeRecipes(recipeList);
            dal.close();
        }
    }
}