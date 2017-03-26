package com.schachte.android.proactive_food_app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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
}

