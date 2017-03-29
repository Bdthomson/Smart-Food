package com.schachte.android.proactive_food_app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DataAccessLayer extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "FoodDatabase";


    public DataAccessLayer(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**
     * Called when the database file does not already exist.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SqlQueries.CREATE_FOOD_TABLE);
    }

    /**
     * Called when the file exists but the stored version is older than requested in the constructor.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SqlQueries.DROP_FOOD_TABLE);
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

        List<Recipe> returnRecipes = new ArrayList<>();
        while(cursor.moveToNext()) {
            returnRecipes.add( new Recipe("Test", "Test", null) );
        }

        return returnRecipes;
    }
}

