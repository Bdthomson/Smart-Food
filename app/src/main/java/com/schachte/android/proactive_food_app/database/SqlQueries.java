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

        //Recipe Columns
        public static final String RECIPE_NAME = "recipeName";
        public static final String RECIPE_IMAGE_URL = "imageUrl";
        public static final String RECIPE_IMAGE_BYTES = "imageByteData";
        public static final String RECIPE_SOURCE_URL = "sourceUrl";
        public static final String RECIPE_PROTEIN = "proteinCount";
        public static final String RECIPE_FAT = "fatCount";
        public static final String RECIPE_CARB = "carbCount";
        public static final String RECIPE_INGREDIENTS = "ingredients";
        public static final String RECIPE_READY_MINUTES = "readyInMinutes";
        public static final String RECIPE_RECIPE_ID = "recipeId";
        public static final String RECIPE_SERVINGS = "servings";
        public static final String RECIPE_CALORIES = "calories";
        public static final String RECIPE_FAVORITE = "favorite";

        // Ingredient Columns
        public static final String INGREDIENT_NAME = "ingredientName";
        public static final String INGREDIENT_GENERAL_NAME = "ingredientGeneralName";
        public static final String INGREDIENT_IMAGE_URL = "ingredientImageUrl";
        public static final String INGREDIENT_IMAGE_BYTES = "ingredientImageByteData";
        public static final String INGREDIENT_ID = "ingredientId";

    /*
     * SELECT queries
     */
    public static final String SELECT_ALL_RECIPES = "SELECT * FROM " + RECIPE_TABLE_NAME;
    public static final String SELECT_NOT_FAVORITE_RECIPES = "SELECT * FROM " + RECIPE_TABLE_NAME + " WHERE favorite = 'N'";
    public static final String SELECT_FAVORITE_RECIPES = "SELECT * FROM " + RECIPE_TABLE_NAME + " WHERE favorite = 'Y'";

    public static final String SELECT_ALL_INGREDIENTS = "SELECT * FROM " + INGREDIENT_TABLE_NAME;

    /*
     * INSERT queries
     */


    /*
     * UPDATE queries
     */

    /*
     * DELETE queries
     */
    public static final String DELETE_STORED_RECIPES = "DELETE FROM " + RECIPE_TABLE_NAME;
    public static final String DELETE_STORED_INGREDIENTS = "DELETE FROM " + INGREDIENT_TABLE_NAME;


    /*
     * CREATE table queries
     */
    public static final String CREATE_FOOD_TABLE =  "CREATE TABLE "
            + FOOD_TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_QUANTITY + " INTEGER NOT NULL);";

    public static final String CREATE_INGREDIENT_TABLE = "CREATE TABLE " + INGREDIENT_TABLE_NAME + " ( "
            + INGREDIENT_NAME + " TEXT,"
            + INGREDIENT_GENERAL_NAME + " TEXT,"
            + INGREDIENT_IMAGE_URL + " TEXT,"
            + INGREDIENT_IMAGE_BYTES + " TEXT,"
            + INGREDIENT_ID + " INTEGER)";


    public static final String CREATE_RECIPE_TABLE = "CREATE TABLE " + RECIPE_TABLE_NAME +  "( "
            + RECIPE_NAME           + " TEXT,"
            + RECIPE_IMAGE_URL      + " TEXT,"
            + RECIPE_IMAGE_BYTES    + " TEXT,"
            + RECIPE_SOURCE_URL     + " TEXT,"
            + RECIPE_PROTEIN        + " TEXT,"
            + RECIPE_FAT            + " TEXT,"
            + RECIPE_CARB           + " TEXT,"
            + RECIPE_INGREDIENTS    + " TEXT,"
            + RECIPE_READY_MINUTES  + " INTEGER,"
            + RECIPE_RECIPE_ID      + " INTEGER,"
            + RECIPE_SERVINGS       + " INTEGER,"
            + RECIPE_CALORIES       + " INTEGER,"
            + RECIPE_FAVORITE       + " TEXT DEFAULT 'N')";

    /*
     * DROP table queries
     */
    public static final String DROP_FOOD_TABLE = "DROP TABLE IF EXISTS " + FOOD_TABLE_NAME;
    public static final String DROP_RECIPE_TABLE = "DROP TABLE IF EXISTS " + RECIPE_TABLE_NAME;
    public static final String DROP_INGREDIENT_TABLE = "DROP TABLE IF EXISTS " + INGREDIENT_TABLE_NAME;

}

