package com.schachte.android.proactive_food_app.activities.ingredient_list_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.database.DataAccessLayer;
import com.schachte.android.proactive_food_app.models.Ingredient;

import java.util.ArrayList;

public class PantryActivity extends AppCompatActivity {

    private ListView ingredientListView;
    private IngredientListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        ingredientListView = (ListView) this.findViewById(R.id.pantryListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Ingredient> ingredientList = getPantryIngredients();
        if( ingredientList.size() > 0 ) {
            setAdapterList(ingredientList);
        }
    }

    private ArrayList<Ingredient> getPantryIngredients() {
        DataAccessLayer dal = new DataAccessLayer(this);
        ArrayList<Ingredient> ingredientList = dal.getIngredients();

        if(ingredientList.size() != 0) {
            setAdapterList(ingredientList);
        }

        return ingredientList;
    }

    private void setAdapterList(ArrayList<Ingredient> ingredientList) {
        adapter = new IngredientListAdapter(this, ingredientList);

        //Get the ListView object and assign the adapter to it
        ingredientListView.setAdapter(adapter);
    }
}
