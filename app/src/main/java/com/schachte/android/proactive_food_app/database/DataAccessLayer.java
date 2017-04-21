package com.schachte.android.proactive_food_app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.schachte.android.proactive_food_app.models.PedometerEntry;
import com.schachte.android.proactive_food_app.models.Recipe;
import com.schachte.android.proactive_food_app.util.PedometerSensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.schachte.android.proactive_food_app.database.SqlQueries.RECIPE_CALORIES;
import static com.schachte.android.proactive_food_app.database.SqlQueries.RECIPE_CARB;
import static com.schachte.android.proactive_food_app.database.SqlQueries.RECIPE_FAT;
import static com.schachte.android.proactive_food_app.database.SqlQueries.RECIPE_FAVORITE;
import static com.schachte.android.proactive_food_app.database.SqlQueries.RECIPE_IMAGE_BYTES;
import static com.schachte.android.proactive_food_app.database.SqlQueries.RECIPE_IMAGE_URL;
import static com.schachte.android.proactive_food_app.database.SqlQueries.RECIPE_INGREDIENTS;
import static com.schachte.android.proactive_food_app.database.SqlQueries.RECIPE_NAME;
import static com.schachte.android.proactive_food_app.database.SqlQueries.RECIPE_PROTEIN;
import static com.schachte.android.proactive_food_app.database.SqlQueries.RECIPE_READY_MINUTES;
import static com.schachte.android.proactive_food_app.database.SqlQueries.RECIPE_RECIPE_ID;
import static com.schachte.android.proactive_food_app.database.SqlQueries.RECIPE_SERVINGS;
import static com.schachte.android.proactive_food_app.database.SqlQueries.RECIPE_SOURCE_URL;
import static com.schachte.android.proactive_food_app.database.SqlQueries.RECIPE_TABLE_NAME;


public class DataAccessLayer extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    protected static final String DATABASE_NAME = "FoodDatabase.db";
    public final String TAG = this.getClass().getSimpleName();

    public DataAccessLayer(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("HomeActivity", "This is called for constructor");
    }

    /**
     * Called when the database file does not already exist.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlQueries.CREATE_FOOD_TABLE);
        db.execSQL(SqlQueries.CREATE_PEDOMETER_TABLE);
        db.execSQL(SqlQueries.CREATE_RECIPE_TABLE);
    }

    /**
     * Called when the file exists but the stored version is older than requested in the constructor.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("HomeActivity", "This si called for upgrade");
        db.execSQL(SqlQueries.DROP_FOOD_TABLE);
        db.execSQL(SqlQueries.DROP_RECIPE_TABLE);
        db.execSQL(SqlQueries.DROP_PEDOMETER_TABLE);

        onCreate(db);
    }

    public void storeRecipes(List<Recipe> recipeList) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for ( Recipe recipe : recipeList ) {

                //Create a string with the ignredients separated by "|||"
                StringBuilder ingredients = new StringBuilder();
                for(final String ingredient : recipe.getAllIngredients())
                    ingredients.append(ingredient).append("|||");

                ContentValues values = new ContentValues();
                values.put(RECIPE_NAME, recipe.getRecipeName());
                values.put(RECIPE_IMAGE_URL, recipe.getImageUrl());
                values.put(RECIPE_IMAGE_BYTES, recipe.getImageByteData());
                values.put(RECIPE_SOURCE_URL, recipe.getSourceUrl());
                values.put(RECIPE_PROTEIN, recipe.getProteinCount());
                values.put(RECIPE_FAT, recipe.getFatCount());
                values.put(RECIPE_CARB, recipe.getCarbCount());
                values.put(RECIPE_INGREDIENTS, ingredients.toString());

                values.put(RECIPE_READY_MINUTES, recipe.getReadyInMinutes());
                values.put(RECIPE_RECIPE_ID, recipe.getRecipeId());
                values.put(RECIPE_SERVINGS, recipe.getServings());
                values.put(RECIPE_CALORIES, recipe.getCalories());
                Long result = db.insert(RECIPE_TABLE_NAME, null, values);

                if( result < 0 )
                    System.out.println("Insertion not correctly performed");
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    /*
     * Returns a List of recipes that can be used to create the recipes ListView
     * the Recipe object will also contain the information to display on the recipe
     * deatils page
     */
    public ArrayList<Recipe> getRecipes(String sqlQuery) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<Recipe> recipeList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Recipe recipe = new Recipe();

            recipe.setRecipeName( cursor.getString(cursor.getColumnIndex("recipeName")) );
            recipe.setImageUrl( cursor.getString(cursor.getColumnIndex("imageUrl")) );
            recipe.setImageByteData( cursor.getString(cursor.getColumnIndex("imageByteData")) ); //Raw image blob
            recipe.setSourceUrl( cursor.getString(cursor.getColumnIndex("sourceUrl")) );
            recipe.setProteinCount( cursor.getString(cursor.getColumnIndex("proteinCount")) );
            recipe.setFatCount( cursor.getString(cursor.getColumnIndex("fatCount")) );
            recipe.setCarbCount( cursor.getString(cursor.getColumnIndex("carbCount")) );
            recipe.setIsFavorite( cursor.getString(cursor.getColumnIndex(RECIPE_FAVORITE)));

            recipe.setReadyInMinutes( cursor.getInt(cursor.getColumnIndex("readyInMinutes")) );
            recipe.setRecipeId( cursor.getInt(cursor.getColumnIndex("recipeId")) );
            recipe.setServings( cursor.getInt(cursor.getColumnIndex("servings")) );
            recipe.setCalories( cursor.getInt(cursor.getColumnIndex("calories")) );

            String ingredients = cursor.getString(cursor.getColumnIndex(RECIPE_INGREDIENTS));
            String[] ingredientArray = ingredients.split("\\|\\|\\|");
            List<String> ingredientList = Arrays.asList(ingredientArray);
            recipe.setAllIngredients(ingredientList);

            recipeList.add(recipe);
        }

        cursor.close();
        db.close();
        return recipeList;
    }

    public int getDailyStepCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(TAG, SqlQueries.SELECT_DAILY_STEPS);
        Cursor cursor = db.rawQuery(SqlQueries.SELECT_DAILY_STEPS, null);

        if(cursor.moveToNext()){
            int stepCount = cursor.getInt(cursor.getColumnIndex("steps"));
            return stepCount;
        }

        return 0;
    }

    public int getAverageForNow() {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(TAG, SqlQueries.SELECT_AVG_STEPS);
        Cursor cursor = db.rawQuery(SqlQueries.SELECT_AVG_STEPS, null);

        if(cursor.moveToNext()){
            int stepCount = cursor.getInt(cursor.getColumnIndex("averageSteps"));
            return stepCount;
        }

        return 0;
    }


    public void getAllSteps(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SqlQueries.SELECT_ALL_STEP_RECORDS, null);

        while(cursor.moveToNext()){
            Log.d(TAG, cursor.getString(cursor.getColumnIndex(SqlQueries.KEY_TOTAL_STEPS)));
            Log.d(TAG, cursor.getString(cursor.getColumnIndex(SqlQueries.KEY_CURRENT_TIMESTAMP)));
            Log.d(TAG, "------");


            // Log.d("HomeActivity", pedometerEntries.get(pedometerEntries.size() - 1).toString());
        }
    }

    public List<PedometerEntry> getAllPedometerEntries(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SqlQueries.SELECT_ALL_PEDOMETER_LOGS, null);
        ArrayList<PedometerEntry> pedometerEntries = new ArrayList<>();

        while(cursor.moveToNext()){
            pedometerEntries.add(new PedometerEntry(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex(SqlQueries.KEY_CURRENT_TIMESTAMP)),
                    cursor.getInt(cursor.getColumnIndex(SqlQueries.KEY_TOTAL_STEPS)),
                    cursor.getInt(cursor.getColumnIndex("steps_since_reset"))
            ));

            // Log.d("HomeActivity", pedometerEntries.get(pedometerEntries.size() - 1).toString());
        }

        return pedometerEntries;
    }

    public PedometerEntry getLastPedometerEntry() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SqlQueries.SELECT_LAST_PEDOMETER_RECORD, null);


        PedometerEntry pedEnt = null;

        //TODO: Maybe refactor this. The query is limited to 1 already though.

        //Return an object of the last pedometer log value
        while(cursor.moveToNext()) {
            pedEnt = new PedometerEntry(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex(SqlQueries.KEY_CURRENT_TIMESTAMP)),
                    cursor.getInt(cursor.getColumnIndex(SqlQueries.KEY_TOTAL_STEPS)),
                    cursor.getInt(cursor.getColumnIndex("steps_since_reset"))
            );
        }

        return pedEnt;
    }

    public void insertPedometerLog(float totalSteps, float stepsSinceReset) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SqlQueries.INSERT_PEDOMETER_LOG + "("
                + totalSteps + ", "
                + stepsSinceReset
                + ")");
    }

    //newFavoriteValue = "Y" or "N"
    public void updateRecipeFavorite( int recipeId, String newFavoriteValue ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SqlQueries.RECIPE_FAVORITE, newFavoriteValue);
        db.update(SqlQueries.RECIPE_TABLE_NAME, cv, SqlQueries.RECIPE_RECIPE_ID + "=" + recipeId, null);
        db.close();
    }

    /*
     * Deletes all non-favorite recipes from the database, if the recipe was new it will set it
     * to not be new anymore.
     */
    public void deleteStoredRecipes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SqlQueries.DELETE_STORED_RECIPES);
        db.execSQL(SqlQueries.UPDATE_RECIPES_NOT_NEW);
        db.close();
    }

    public List<String> getIngredients() {

        //TODO: Dougherty to implement this?
        List<String> ingredients = new ArrayList<String>();
        ingredients.add("chocolate");
        ingredients.add("pepperoni");
        ingredients.add("ham");
        ingredients.add("cilantro");
        ingredients.add("tortillas");
        ingredients.add("noodles");

        return ingredients;
    }
}

