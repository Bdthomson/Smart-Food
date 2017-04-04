package com.schachte.android.proactive_food_app.database;

/**
 * Created by Spencer Smith on 3/25/2017.
 */

public final class SqlQueries {

    /*
     *  Name constants
     */

    /* Tables */
    public static final String FOOD_TABLE_NAME = "food";
    public static final String RECIPE_TABLE_NAME = "recipes";

    /* Columns */
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_QUANTITY = "quantity";

    /*
     * SELECT queries
     */
    public static final String SELECT_ALL_RECIPES = "SELECT * FROM " + RECIPE_TABLE_NAME;


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

    /*
    CREATE TABLE recipes (
        id            TEXT
        recipeName    TEXT,
        ingredients   TEXT,
        prepTime      INTEGER,
        rating        INTEGER,
        largeImageUrl TEXT,
    );
     */
    /*
     * DROP table queries
     */
    public static final String DROP_FOOD_TABLE = "DROP TABLE IF EXISTS " + FOOD_TABLE_NAME;


}

