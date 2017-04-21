package com.schachte.android.proactive_food_app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.Result;
import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.activities.add_ingredient_activities.AutoIngredientActivity;
import com.schachte.android.proactive_food_app.activities.add_ingredient_activities.ManualIngredientActivity;
import com.schachte.android.proactive_food_app.activities.ingredient_list_activity.PantryActivity;
import com.schachte.android.proactive_food_app.database.ClientRequests;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class AddIngredientsActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    Button barcodeBtn;
    Button manualBtn;
    Button ingredientsBtn;
    private ZXingScannerView zXingScannerView;
    TextView tv;
    ClientRequests cR;
    private static Context context;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        context = getApplicationContext();
        registerButtonListeners();
    }

    /**
     * Adding click functionality to fab buttons and menu choices
     */
    public void registerButtonListeners() {

        barcodeBtn = (Button) findViewById(R.id.barcode_button);
        manualBtn = (Button) findViewById(R.id.manual_button);
        ingredientsBtn = (Button) findViewById(R.id.view_pantry_btn_ingredients);

        cR = new ClientRequests(getApplicationContext());

        barcodeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            // cR.makeReq();
            beginBarcodeScan();
            }
        });

        manualBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddIngredientsActivity.this, ManualIngredientActivity.class);
                startActivity(intent);
            }
        });

        ingredientsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddIngredientsActivity.this, PantryActivity.class);
                startActivity(intent);
            }
        });
    }

    public void beginBarcodeScan() {
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            if (zXingScannerView != null) {
                zXingScannerView.stopCamera();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleResult(Result result) {
        zXingScannerView.removeAllViews(); //<- here remove all the views, it will make an Activity having no View
        zXingScannerView.stopCamera(); //<- then stop the camera
        setContentView(R.layout.activity_ingredients); //<- and set the View again.

        // Now load the AutoIngredientActivity
        Intent intent = new Intent(getBaseContext(), AutoIngredientActivity.class);
        intent.putExtra("JSON_ID", result.getText());
        startActivity(intent);
    }
}
