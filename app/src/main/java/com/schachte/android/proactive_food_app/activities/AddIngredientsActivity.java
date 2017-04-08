package com.schachte.android.proactive_food_app.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.zxing.Result;
import com.schachte.android.proactive_food_app.R;
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
                String pName = null;
                Log.d(TAG, "About to make the request");

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
            zXingScannerView.stopCamera();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleResult(Result result) {
        // Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
        zXingScannerView.removeAllViews(); //<- here remove all the views, it will make an Activity having no View
        zXingScannerView.stopCamera(); //<- then stop the camera
        setContentView(R.layout.activity_ingredients); //<- and set the View again.
        tv = (TextView) findViewById(R.id.barcodeID);
        cR.makeReq(result.getText() + ".json");
        tv.setText(result.getText());
        registerButtonListeners();
    }
}
