package com.schachte.android.proactive_food_app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.schachte.android.proactive_food_app.models.PedometerEntry;
import com.schachte.android.proactive_food_app.models.Recipe;
import com.schachte.android.proactive_food_app.util.PedometerSensor;

import java.util.ArrayList;
import java.util.List;


public class DataAccessLayer extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    protected static final String DATABASE_NAME = "FoodDatabase.db";

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
        // db.execSQL(SqlQueries.CREATE_RECIPE_TABLE);
        db.execSQL(SqlQueries.CREATE_PEDOMETER_TABLE);

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

        onCreate(db);
    }

    /*
     * Returns a List of recipes that can be used to create the recipes ListView
     * the Recipe object will also contain the information to display on the recipe
     * deatils page
     */
    public List<Recipe> getRecipes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SqlQueries.SELECT_ALL_RECIPES, null);

        List<Recipe> recipeList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Recipe recipe = new Recipe();

            recipe.setRecipeName( cursor.getString(cursor.getColumnIndex("recipeName")) );
            recipe.setImageUrl( cursor.getString(cursor.getColumnIndex("imageUrl")) );
            recipe.setImageByteData( cursor.getString(cursor.getColumnIndex("imageByteData")) ); //Raw image blob
            recipe.setSourceUrl( cursor.getString(cursor.getColumnIndex("sourceUrl")) );
            recipe.setProteinCount( cursor.getString(cursor.getColumnIndex("proteinCount")) );
            recipe.setFatCount( cursor.getString(cursor.getColumnIndex("fatCount")) );
            recipe.setCarbCount( cursor.getString(cursor.getColumnIndex("carbCount")) );

            recipe.setReadyInMinutes( cursor.getInt(cursor.getColumnIndex("readyInMinutes")) );
            recipe.setRecipeId( cursor.getInt(cursor.getColumnIndex("recipeId")) );
            recipe.setServings( cursor.getInt(cursor.getColumnIndex("servings")) );
            recipe.setCalories( cursor.getInt(cursor.getColumnIndex("calories")) );

            recipeList.add(recipe);
        }

        return recipeList;
    }

    public List<PedometerEntry> getAllPedometerEntries(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SqlQueries.SELECT_ALL_PEDOMETER_LOGS, null);
        ArrayList<PedometerEntry> pedometerEntries = new ArrayList<>();

        while(cursor.moveToNext()){
            pedometerEntries.add(new PedometerEntry(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("timestamp")),
                    cursor.getInt(cursor.getColumnIndex("total_steps")),
                    cursor.getInt(cursor.getColumnIndex("steps_since_reset"))
            ));

            Log.d("HomeActivity", pedometerEntries.get(pedometerEntries.size() - 1).toString());
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
                    cursor.getString(cursor.getColumnIndex("timestamp")),
                    cursor.getInt(cursor.getColumnIndex("total_steps")),
                    cursor.getInt(cursor.getColumnIndex("steps_since_reset"))
            );
        }

        return pedEnt;
    }

    public void insertPedometerLog(float totalSteps, float stepsSinceReset){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SqlQueries.INSERT_PEDOMETER_LOG + "("
                + totalSteps + ", "
                + stepsSinceReset
                + ")");

    }
}

