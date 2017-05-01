package com.schachte.android.proactive_food_app.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;

import com.google.zxing.Result;
import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.activities.add_ingredient_activities.AutoIngredientActivity;
import com.schachte.android.proactive_food_app.activities.add_ingredient_activities.ManualIngredientActivity;
import com.schachte.android.proactive_food_app.activities.ingredient_list_activity.PantryActivity;
import com.schachte.android.proactive_food_app.database.ClientRequests;
import com.schachte.android.proactive_food_app.util.WebServices;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

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

        //Code from Stackoverflow to request camera permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
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
                Log.d("HomeActivity", "This button get's clicked");
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

        Log.d("HomeActivity", "Inside this else");
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
        registerButtonListeners();
        // Now load the AutoIngredientActivity

        if (WebServices.isNetworkAvailable(this)) {
            Intent intent = new Intent(getBaseContext(), AutoIngredientActivity.class);
            intent.putExtra("JSON_ID", result.getText());
            startActivity(intent);
        } else {
            //Kills activity since no internet connection for given task
            new LovelyStandardDialog(this)
                    .setTopColorRes(R.color.network_issues)
                    .setIcon(R.drawable.wifi)
                    .setTitle(R.string.network_issues_info)
                    .setMessage(R.string.network_issues_info)
                    .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // finish();
                        }
                    })
                    .show();
        }
    }
}
