package com.schachte.android.proactive_food_app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.schachte.android.proactive_food_app.R;

import static com.schachte.android.proactive_food_app.database.Preferences.getInstance;

public class PreferencesActivity extends AppCompatActivity {

    Button backButton;
    Button saveButton;

    EditText name;
    EditText age;
    EditText height;
    EditText weight;
    RadioGroup rg;
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

    private boolean validateInput(){

        boolean valid = true;
        int ageValue, weightValue, heightValue;
        ageValue = 0;
        weightValue = 0;
        heightValue = 0;

        // Name Required
        if (name.getText().toString().trim().equalsIgnoreCase("")){
            valid = false;
            name.setError("Name required.");
        }

        // Age Required
        try {
            ageValue = Integer.parseInt(age.getText().toString());
        } catch (NumberFormatException e){
            valid = false;
            age.setError("Age Required");
        }

        // Age must be in valid range. 13 - 130.
        if (age.getError() == null){
            if (ageValue < 13 || ageValue > 130){
                valid = false;
                age.setError("Invalid Age");
            }
        }

        // Weight Required
        try {
            weightValue = Integer.parseInt(weight.getText().toString());
        } catch (NumberFormatException e){
            valid = false;
            weight.setError("Weight Required");
        }

        // Weight must be in valid range. 40 - 1400.
        if (weight.getError() == null){
            if (weightValue < 13 || weightValue > 1400){
                valid = false;
                weight.setError("Invalid Weight");
            }
        }

        // Height Required
        try {
            heightValue = Integer.parseInt(height.getText().toString());
        } catch (NumberFormatException e){
            valid = false;
            height.setError("Height Required");
        }

        //  must be in valid range (inches). 10 - 100.
        if (height.getError() == null){
            if (heightValue < 10 || heightValue > 100){
                valid = false;
                height.setError("Invalid Height");
            }
        }

        return valid;
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

                boolean valid = validateInput();
                if (valid) {
                    getInstance().writePreferenceString("name", name.getText().toString());
                    getInstance().writePreferenceInt("age", Integer.parseInt(age.getText().toString()));
                    getInstance().writePreferenceInt("height", Integer.parseInt(height.getText().toString()));
                    getInstance().writePreferenceInt("weight", Integer.parseInt(weight.getText().toString()));
                    getInstance().writePreferenceInt("gender", rg.getCheckedRadioButtonId());
                    getInstance().writePreferenceInt("activity_level", activityLevel.getProgress());

                    if (!getInstance().getPreferenceBool("setupComplete")) {
                        getInstance().writePreferenceBool("setupComplete", true);
                        Intent intent = new Intent(PreferencesActivity.this, HomeActivity.class);
                        finish();  //Kill the activity from which you will go to next activity
                        startActivity(intent);
                    } else {
                        finish();
                    }
                }
            }
        });
    }
}
