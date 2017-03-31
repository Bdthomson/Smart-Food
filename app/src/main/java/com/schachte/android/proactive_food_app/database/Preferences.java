package com.schachte.android.proactive_food_app.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * This is a singleton class for instantiating one object of the userprefs
 * object throughout the entire application. This is where you will call and store
 * key/value pairs for the user-prefs stuff.
 *
 * @Contact: Ryan Schachte
 */

public class Preferences {
    private static Preferences prefsInstance;
    private Context prefsContext;
    private SharedPreferences userSharedPreferences;

    private Preferences() {};

    public static Preferences getInstance() {
        if (prefsInstance == null) prefsInstance = new Preferences();
        return prefsInstance;
    }

    public void Initialize(Context ctxt) {
        prefsContext = ctxt;
        userSharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctxt);
    }

    public String getPreference(String key){
        SharedPreferences prefs = userSharedPreferences;
        return prefs.getString(key, null);
    }

    public void writePreference(String key, String value){
        SharedPreferences.Editor e = userSharedPreferences.edit();
        e.putString(key, value);
        e.commit();
    }
}
