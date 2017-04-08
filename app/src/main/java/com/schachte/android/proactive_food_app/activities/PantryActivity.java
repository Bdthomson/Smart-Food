package com.schachte.android.proactive_food_app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.models.Ingredient;
import com.schachte.android.proactive_food_app.models.PantryData;

import java.util.ArrayList;
import java.util.List;

public class PantryActivity extends AppCompatActivity {

    private ListView pantryItemsListView;;

    private List<String> ingredientNames = new ArrayList<>();

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        pantryItemsListView = (ListView)findViewById(R.id.pantryListView);

        // Some test data
        PantryData pd = new PantryData();
        pd.addUserIngredient(new Ingredient("tester"));

        List<Ingredient> ingredients = pd.getUserIngredients();

        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredientNames);

        pantryItemsListView.setAdapter(adapter);

        for (Ingredient ingredient : ingredients) {
            ingredientNames.add(ingredient.getName());
            if (ingredient.getBlob() != null) {
                String blob = ingredient.getBlob();
            }
            adapter.notifyDataSetChanged();
        }
    }
}
