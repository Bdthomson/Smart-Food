package com.schachte.android.proactive_food_app.database;

/**
 * Created by Spencer Smith on 3/25/2017.
 *
 * This file should hold all query strings that will be used by the DataAccessLayer
 * class.  It is much easier to maintain a file like this than have it jumbling up the
 * DAL class.
 */

public final class SqlQueries {

    /*
     *  Name constants
     */

    /* Tables */
    public static final String FOOD_TABLE_NAME = "food";
    public static final String RECIPE_TABLE_NAME = "recipes";
    public static final String INGREDIENT_TABLE_NAME = "ingredients";

    /* Columns */
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_QUANTITY = "quantity";


    /*
     * SELECT queries
     */
    public static final String SELECT_ALL_RECIPES = "SELECT * FROM " + RECIPE_TABLE_NAME;
    public static final String SELECT_ALL_INGREDIENTS = "SELECT * FROM " + INGREDIENT_TABLE_NAME;


    /*
     * INSERT queries
     */


    /*
     * UPDATE queries
     */


    /*
     * CREATE table queries
     */
    public static final String CREATE_FOOD_TABLE =  "CREATE TABLE "
            + FOOD_TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT NOT NULL,"
            + KEY_QUANTITY + " INTEGER NOT NULL);";


    public static final String CREATE_INGREDIENT_TABLE = "CREATE TABLE " + INGREDIENT_TABLE_NAME + " ( "
            + "ingredientName      TEXT,"
            + "imageUrl        TEXT,"
            + "imageByteData   TEXT,"
            + "sourceUrl       TEXT,"
            + "ingredientId    INTEGER)";

    public static final String CREATE_RECIPE_TABLE = "CREATE TABLE " + RECIPE_TABLE_NAME + " ( "
            + "recipeName      TEXT,"
            + "imageUrl        TEXT,"
            + "imageByteData   TEXT,"
            + "sourceUrl       TEXT,"
            + "proteinCount    TEXT,"
            + "fatCount        TEXT,"
            + "carbCount       TEXT,"
            + "readyInMinutes  INTEGER,"
            + "recipeId        INTEGER,"
            + "servings        INTEGER,"
            + "calories        INTEGER)";

    /*
     * DROP table queries
     */
    public static final String DROP_FOOD_TABLE = "DROP TABLE IF EXISTS " + FOOD_TABLE_NAME;


}

