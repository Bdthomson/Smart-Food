package com.schachte.android.proactive_food_app.database;

/**
 * Created by Spencer Smith on 3/25/2017.
 */

public final class SqlQueries {

    /*
     * COLUMN name constants
     */
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_QUANTITY = "quantity";

    /*
     * SELECT queries
     */



    /*
     * INSERT queries
     */


    /*
     * UPDATE queries
     */


    /*
     * CREATE table queries
     */
    public static final String FOOD_TABLE_NAME = "food";
    public static final String CREATE_FOOD_TABLE =  "CREATE TABLE "
            + FOOD_TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT NOT NULL,"
            + KEY_QUANTITY + " INTEGER NOT NULL);";

    /*
     * DROP table queries
     */
    public static final String DROP_FOOD_TABLE = "DROP TABLE IF EXISTS " + FOOD_TABLE_NAME;


}
