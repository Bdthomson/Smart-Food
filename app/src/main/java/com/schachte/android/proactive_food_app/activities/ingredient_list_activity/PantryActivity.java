package com.schachte.android.proactive_food_app.activities.ingredient_list_activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.database.DataAccessLayer;
import com.schachte.android.proactive_food_app.models.Ingredient;

import java.util.ArrayList;

public class PantryActivity extends AppCompatActivity {

    private ListView ingredientListView;
    private IngredientListAdapter adapter;
    private ArrayList<Ingredient> ingredientList;
    private DataAccessLayer dal = new DataAccessLayer(this);

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

        this.ingredientList = dal.getIngredients();

        if(ingredientList.size() != 0) {
            setAdapterList(ingredientList);
        }

        return ingredientList;
    }

    private void setAdapterList(final ArrayList<Ingredient> ingredientList) {
        adapter = new IngredientListAdapter(this, ingredientList);

        //Get the ListView object and assign the adapter to it
        ingredientListView.setAdapter(adapter);

        ingredientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(PantryActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete this?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<Ingredient> copied = (ArrayList<Ingredient>)ingredientList.clone();
                        copied.remove(positionToRemove);
                        dal.deleteStoredIngredients();
                        dal.storeIngredients(copied);
                        ingredientListView.setAdapter(new IngredientListAdapter(PantryActivity.this, copied));
                        adapter.notifyDataSetChanged();
                    }});
                adb.show();
            }
        });
    }
}
