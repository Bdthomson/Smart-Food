package com.schachte.android.proactive_food_app.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

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

    // For explanation, please see:
    // http://stackoverflow.com/questions/21396358/sharedpreferences-putstringset-doesnt-work
    // TLDR: Have to create a copy of the object so it will recognize that the
    // object has changed.
    public Set<String> getPreferenceStringSet(String key){
        SharedPreferences prefs = userSharedPreferences;
        Set<String> tempSet = prefs.getStringSet(key, new HashSet<String>());
        return new HashSet<>(tempSet);
    }

    public String getPreferenceString(String key){
        SharedPreferences prefs = userSharedPreferences;
        return prefs.getString(key, null);
    }

    public boolean getPreferenceBool(String key){
        SharedPreferences prefs = userSharedPreferences;
        return prefs.getBoolean(key, false);
    }

    public int getPreferenceInt(String key){
        SharedPreferences prefs = userSharedPreferences;
        return prefs.getInt(key, 0);
    }


    public void writePreferenceStringSet(String key, Set<String> ss){
        SharedPreferences.Editor e = userSharedPreferences.edit();
        e.putStringSet(key, ss);
        e.commit();
    }

    public void writePreferenceString(String key, String value){
        SharedPreferences.Editor e = userSharedPreferences.edit();
        e.putString(key, value);
        e.commit();
    }

    public void writePreferenceBool(String key, Boolean value){
        SharedPreferences.Editor e = userSharedPreferences.edit();
        e.putBoolean(key, value);
        e.commit();
    }

    public void writePreferenceInt(String key, int value){
        SharedPreferences.Editor e = userSharedPreferences.edit();
        e.putInt(key, value);
        e.commit();
    }
}
