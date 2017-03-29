package com.schachte.android.proactive_food_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import org.json.JSONArray;

public class Preferences extends AppCompatActivity {

    Button backButton;
    Button saveButton;

    public final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        registerButtonListeners();
    }

    private void registerButtonListeners() {

        // The save button loads the selected choices from UI and saves them to shared preferences.
        backButton = (Button) findViewById(R.id.button_back);
        saveButton = (Button) findViewById(R.id.button_save);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Save User Preferences

                // We need an Editor object to make preference changes.
                // All objects are from android.context.Context
                SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();

                EditText name = (EditText) findViewById(R.id.editTextName);
                EditText age = (EditText) findViewById(R.id.editTextAge);
                EditText height = (EditText) findViewById(R.id.editTextHeight);
                EditText weight = (EditText) findViewById(R.id.editTextWeight);

                editor.putString("name", name.getText().toString());
                editor.putInt("age", Integer.parseInt(age.getText().toString()));
                editor.putInt("height", Integer.parseInt(height.getText().toString()));
                editor.putInt("weight", Integer.parseInt(weight.getText().toString()));

                RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupGender);
                String radiovalue = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();

                editor.putString("gender", radiovalue);

                // Commit the changes.
                editor.commit();

                SeekBar activityLevel = (SeekBar) findViewById(R.id.activity_level_seekbar);
                editor.putInt("activity_level", activityLevel.getProgress());

                Intent i = new Intent(Preferences.this, Home.class);
                finish();  //Kill the activity from which you will go to next activity
                startActivity(i);
            }
        });
    }
}
