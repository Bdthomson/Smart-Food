package com.schachte.android.proactive_food_app.activities.add_ingredient_activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.database.DataAccessLayer;
import com.schachte.android.proactive_food_app.models.Ingredient;

public class ManualIngredientActivity extends Activity {

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

    }

    private void addIngredient() {
        TextView tv = (TextView)findViewById(R.id.nameOfIngredient);
        Log.i(tv.getText().toString(), tv.getText().toString());
        if (tv.getText().length() == 0) {
            // TODO: display
        } else {
            String nameOfIngredient = tv.getText().toString();
            Ingredient toAdd = new Ingredient();
            toAdd.setIngredientName(nameOfIngredient);

            // I think this is okay for now...
            toAdd.setIngredientGeneralName(nameOfIngredient);

            toAdd.setIngredientId(0);
            toAdd.setIngredientImageBytes("none");
            toAdd.setIngredientImageURL("none");

            DataAccessLayer dal = new DataAccessLayer(this);
            dal.storeIngredient(toAdd);
        }
    }

}
