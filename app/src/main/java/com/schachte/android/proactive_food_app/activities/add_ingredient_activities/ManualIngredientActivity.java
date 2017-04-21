package com.schachte.android.proactive_food_app.activities.add_ingredient_activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.database.DataAccessLayer;
import com.schachte.android.proactive_food_app.models.Ingredient;

public class ManualIngredientActivity extends Activity {

    private EditText normalName;
    private EditText generalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_ingredient);

        Button addIngredientButton = (Button) findViewById(R.id.addManualIngredientBtn);

        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addIngredient();
            }
        });

        normalName = (EditText)findViewById(R.id.nameOfIngredient);
        generalName = (EditText)findViewById(R.id.manualGeneralTextView);

        normalName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    normalName.setHint("");
                } else {
                    normalName.setHint("Name");
                }
            }
        });

        generalName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    generalName.setHint("");
                } else {
                    generalName.setHint("Name");
                }
            }
        });
    }

    private void addIngredient() {
        Log.i(normalName.getText().toString(), normalName.getText().toString());
        if (normalName.getText().length() == 0) {
            Toast.makeText(this, "You must put some name of an ingredient!", Toast.LENGTH_LONG).show();
        } else {
            String nameOfIngredient = normalName.getText().toString();
            Ingredient toAdd = new Ingredient();
            toAdd.setIngredientName(nameOfIngredient);

            EditText general = (EditText)findViewById(R.id.manualGeneralTextView);
            String generalNameOfIngredient = general.getText().toString();
            toAdd.setIngredientGeneralName(generalNameOfIngredient);

            toAdd.setIngredientId(0);
            toAdd.setIngredientImageBytes("none");
            toAdd.setIngredientImageURL("none");

            DataAccessLayer dal = new DataAccessLayer(this);
            dal.storeIngredient(toAdd);
            finish();
        }
    }

}
