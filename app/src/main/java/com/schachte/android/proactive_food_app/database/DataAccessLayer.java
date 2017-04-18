package com.schachte.android.proactive_food_app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.schachte.android.proactive_food_app.models.Ingredient;
import com.schachte.android.proactive_food_app.models.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.schachte.android.proactive_food_app.database.SqlQueries.INGREDIENT_GENERAL_NAME;
import static com.schachte.android.proactive_food_app.database.SqlQueries.INGREDIENT_ID;
import static com.schachte.android.proactive_food_app.database.SqlQueries.INGREDIENT_IMAGE_BYTES;
import static com.schachte.android.proactive_food_app.database.SqlQueries.INGREDIENT_IMAGE_URL;
import static com.schachte.android.proactive_food_app.database.SqlQueries.INGREDIENT_NAME;
import static com.schachte.android.proactive_food_app.database.SqlQueries.INGREDIENT_TABLE_NAME;
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
        db.execSQL(SqlQueries.CREATE_RECIPE_TABLE);
        db.execSQL(SqlQueries.CREATE_INGREDIENT_TABLE);
    }

    /**
     * Called when the file exists but the stored version is older than requested in the constructor.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SqlQueries.DROP_RECIPE_TABLE);
        db.execSQL(SqlQueries.DROP_FOOD_TABLE);
        db.execSQL(SqlQueries.DROP_INGREDIENT_TABLE);

        onCreate(db);
    }

    public void storeIngredient(Ingredient ingredient) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(INGREDIENT_NAME, ingredient.getIngredientName());
            values.put(INGREDIENT_GENERAL_NAME, ingredient.getIngredientGeneralName());
            values.put(INGREDIENT_IMAGE_URL, ingredient.getIngredientImageURL());
            values.put(INGREDIENT_IMAGE_BYTES, ingredient.getIngredientImageBytes());
            values.put(INGREDIENT_ID, ingredient.getIngredientId());
            Long result = db.insert(INGREDIENT_TABLE_NAME, null, values);

            if ( result < 0 ) {
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


    public void storeIngredients(List<Ingredient> ingredientList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for ( Ingredient ingredient : ingredientList ) {

                ContentValues values = new ContentValues();
                values.put(INGREDIENT_NAME, ingredient.getIngredientName());
                values.put(INGREDIENT_GENERAL_NAME, ingredient.getIngredientGeneralName());
                values.put(INGREDIENT_IMAGE_URL, ingredient.getIngredientImageURL());
                values.put(INGREDIENT_IMAGE_BYTES, ingredient.getIngredientImageBytes());
                values.put(INGREDIENT_ID, ingredient.getIngredientId());
                Long result = db.insert(INGREDIENT_TABLE_NAME, null, values);

                if ( result < 0 )
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

                if ( result < 0 )
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
     * Returns a List of ingredients that can be used to create the ingredients ListView
     * the Ingredient object will also contain the information to display on the ingredient
     * details page
     */
    public ArrayList<Ingredient> getIngredients() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(SqlQueries.SELECT_ALL_INGREDIENTS, null);

        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Ingredient ingredient = new Ingredient();

            ingredient.setIngredientName(cursor.getString(cursor.getColumnIndex(INGREDIENT_NAME)));
            ingredient.setIngredientGeneralName(cursor.getString(cursor.getColumnIndex(INGREDIENT_GENERAL_NAME)));
            ingredient.setIngredientImageURL(cursor.getString(cursor.getColumnIndex(INGREDIENT_IMAGE_URL)));
            ingredient.setIngredientImageBytes(cursor.getString(cursor.getColumnIndex(INGREDIENT_IMAGE_BYTES)));
            ingredient.setIngredientId(cursor.getInt(cursor.getColumnIndex(INGREDIENT_ID)));

            ingredientList.add(ingredient);
        }

        cursor.close();
        db.close();
        return ingredientList;
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

            recipe.setRecipeName( cursor.getString(cursor.getColumnIndex(RECIPE_NAME)) );
            recipe.setImageUrl( cursor.getString(cursor.getColumnIndex(RECIPE_IMAGE_URL)) );
            recipe.setImageByteData( cursor.getString(cursor.getColumnIndex(RECIPE_IMAGE_BYTES)) ); //Raw image blob
            recipe.setSourceUrl( cursor.getString(cursor.getColumnIndex(RECIPE_SOURCE_URL)) );
            recipe.setProteinCount( cursor.getString(cursor.getColumnIndex(RECIPE_PROTEIN)) );
            recipe.setFatCount( cursor.getString(cursor.getColumnIndex(RECIPE_FAT)) );
            recipe.setCarbCount( cursor.getString(cursor.getColumnIndex(RECIPE_CARB)) );
            recipe.setIsFavorite( cursor.getString(cursor.getColumnIndex(RECIPE_FAVORITE)));

            recipe.setReadyInMinutes( cursor.getInt(cursor.getColumnIndex(RECIPE_READY_MINUTES)) );
            recipe.setRecipeId( cursor.getInt(cursor.getColumnIndex(RECIPE_RECIPE_ID)) );
            recipe.setServings( cursor.getInt(cursor.getColumnIndex(RECIPE_SERVINGS)) );
            recipe.setCalories( cursor.getInt(cursor.getColumnIndex(RECIPE_CALORIES)) );

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

    //newFavoriteValue = "Y" or "N"
    public void updateRecipeFavorite( int recipeId, String newFavoriteValue ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SqlQueries.RECIPE_FAVORITE, newFavoriteValue);
        db.update(SqlQueries.RECIPE_TABLE_NAME, cv, SqlQueries.RECIPE_RECIPE_ID + "=" + recipeId, null);
        db.close();
    }

    /*
     * Deletes all non-favorite recipes from the database
     */
    public void deleteStoredRecipes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SqlQueries.DELETE_STORED_RECIPES);
        db.close();
    }


}

