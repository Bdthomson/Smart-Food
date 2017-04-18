package com.schachte.android.proactive_food_app.activities.ingredient_list_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.schachte.android.proactive_food_app.R;

public class PantryActivity extends AppCompatActivity {

    private ListView ingredientListView;
    private IngredientListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
    }
}
