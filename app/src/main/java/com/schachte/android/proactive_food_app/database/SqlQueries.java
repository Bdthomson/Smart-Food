package com.schachte.android.proactive_food_app.database;

import com.schachte.android.proactive_food_app.util.TimeHelper;

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
    public static final String PEDOMETER_TABLE_NAME = "steps";
    public static final String INGREDIENT_TABLE_NAME = "ingredients";

    /* Columns */
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_QUANTITY = "quantity";

    // Pedometer Keys
    public static final String KEY_CURRENT_TIMESTAMP = "dt";
    public static final String KEY_TOTAL_STEPS = "steps";
    public static final String KEY_STEPS_SINCE_LAST_RESET = "steps_since_reset";

    /*
     * SELECT queries
     */
    public static final String SELECT_ALL_RECIPES = "SELECT * FROM " + RECIPE_TABLE_NAME;
    public static final String SELECT_ALL_INGREDIENTS = "SELECT * FROM " + INGREDIENT_TABLE_NAME;
    public static final String SELECT_ALL_PEDOMETER_LOGS = "SELECT * FROM " + PEDOMETER_TABLE_NAME;
    public static final String SELECT_LAST_PEDOMETER_RECORD = "SELECT * FROM " + PEDOMETER_TABLE_NAME
            + " ORDER BY " + KEY_ID + " DESC LIMIT 1;";

    public static final String SELECT_ALL_STEP_RECORDS = "SELECT * FROM " + PEDOMETER_TABLE_NAME;

    public static final String SELECT_DAILY_STEPS = "SELECT b.steps - a.steps as steps, b.dt" +
            "	FROM" +
            "		(SELECT *" +
            "			FROM steps" +
            "				WHERE dt = (SELECT MIN(dt) from steps where strftime('%Y-%m-%d %H:%M:%S', dt ) BETWEEN '" + TimeHelper.getDateNow() + " 00:00:00' AND datetime('now', 'localtime'))) a," +
            "		(SELECT *" +
            "			FROM steps" +
            "				WHERE dt = (SELECT MAX(dt) from steps where strftime('%Y-%m-%d %H:%M:%S', dt ) BETWEEN '" + TimeHelper.getDateNow() + " 00:00:00' AND datetime('now', 'localtime'))) b";


    public static final String SELECT_AVG_STEPS = "SELECT AVG(difference) as averageSteps " +
            "FROM" +
            "	(SELECT maxSteps.steps - minSteps.steps as difference, minSteps.day" +
            "	FROM" +
            "		(SELECT steps, bounds.day" +
            "		FROM steps, " +
            "			(select min(dt) as minTime, strftime('%Y-%m-%d',dt) as day" +
            "			from steps" +
            "			where strftime('%H:%M:%S', dt ) < time('now', 'localtime')" +
            "			and date('now', 'localtime') <= datetime( strftime('%Y-%m-%d', dt ), '+14 days' )" +
            "			group by strftime('%Y-%m-%d',dt) ) bounds" +
            "		WHERE dt = bounds.minTime) minSteps," +
            "		(SELECT steps, bounds.day" +
            "		FROM steps, " +
            "			(select max(dt) as maxTime, strftime('%Y-%m-%d',dt) as day" +
            "			from steps" +
            "			where strftime('%H:%M:%S', dt ) < time('now', 'localtime')" +
            "			and date('now', 'localtime') <= datetime( strftime('%Y-%m-%d', dt ), '+14 days' )" +
            "			group by strftime('%Y-%m-%d',dt) ) bounds" +
            "		WHERE dt = bounds.maxTime) maxSteps" +
            "	WHERE minSteps.day = maxSteps.day)";

    /*
     * INSERT queries
     */
    public static final String INSERT_PEDOMETER_LOG = "INSERT INTO " + PEDOMETER_TABLE_NAME
            + "(" + KEY_TOTAL_STEPS + ", " + KEY_STEPS_SINCE_LAST_RESET + ") "
            + " VALUES ";

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

    public static final String CREATE_PEDOMETER_TABLE =  "CREATE TABLE "
            + PEDOMETER_TABLE_NAME          + "("
            + KEY_ID                        + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_CURRENT_TIMESTAMP         + " TIMESTAMP NOT NULL DEFAULT (datetime('now','localtime')),"
            + KEY_TOTAL_STEPS               + " FLOAT NOT NULL,"
            + KEY_STEPS_SINCE_LAST_RESET    + " FLOAT NOT NULL);";

    /*
     * DROP table queries
     */
    public static final String DROP_FOOD_TABLE = "DROP TABLE IF EXISTS " + FOOD_TABLE_NAME;
    public static final String DROP_RECIPE_TABLE = "DROP TABLE IF EXISTS " + RECIPE_TABLE_NAME;
    public static final String DROP_PEDOMETER_TABLE = "DROP TABLE IF EXISTS " + PEDOMETER_TABLE_NAME;

}

