package com.schachte.android.proactive_food_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IntegerRes;
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

import static com.schachte.android.proactive_food_app.database.Preferences.getInstance;

public class Preferences extends AppCompatActivity {

    Button backButton;
    Button saveButton;

    EditText name;
    EditText age;
    EditText height;
    EditText weight;
    RadioGroup rg;
    RadioButton radioButton;
    String radioValue;
    SeekBar activityLevel;

    public final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        name = (EditText) findViewById(R.id.editTextName);
        age = (EditText) findViewById(R.id.editTextAge);
        height = (EditText) findViewById(R.id.editTextHeight);
        weight = (EditText) findViewById(R.id.editTextWeight);
        rg = (RadioGroup) findViewById(R.id.radioGroupGender);
        activityLevel = (SeekBar) findViewById(R.id.activity_level_seekbar);

        // Load values from preferences if setup is complete.
        if (getInstance().getPreferenceBool("setupComplete")) {
            name.setText(getInstance().getPreferenceString("name"));
            age.setText(String.valueOf(getInstance().getPreferenceInt("age")));
            height.setText(String.valueOf(getInstance().getPreferenceInt("height")));
            weight.setText(String.valueOf(getInstance().getPreferenceInt("weight")));
            activityLevel.setProgress(getInstance().getPreferenceInt("activity_level"));
            rg.check(getInstance().getPreferenceInt("gender"));
        }

        registerButtonListeners();
    }

    private void registerButtonListeners() {

        // The save button loads the selected choices from UI and saves them to shared preferences.
        backButton = (Button) findViewById(R.id.button_back);
        saveButton = (Button) findViewById(R.id.button_save);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }});

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                getInstance().writePreferenceString("name", name.getText().toString());
                getInstance().writePreferenceInt("age", Integer.parseInt(age.getText().toString()));
                getInstance().writePreferenceInt("height", Integer.parseInt(height.getText().toString()));
                getInstance().writePreferenceInt("weight", Integer.parseInt(weight.getText().toString()));
                getInstance().writePreferenceInt("gender", rg.getCheckedRadioButtonId());
                getInstance().writePreferenceInt("activity_level", activityLevel.getProgress());

                if (!getInstance().getPreferenceBool("setupComplete")){
                    getInstance().writePreferenceBool("setupComplete", true);
                    Intent intent = new Intent(Preferences.this, Home.class);
                    finish();  //Kill the activity from which you will go to next activity
                    startActivity(intent);
                } else {
                    finish();
                }
            }
        });
    }
}
