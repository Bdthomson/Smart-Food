package com.schachte.android.proactive_food_app;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.zxing.Result;
import com.schachte.android.proactive_food_app.database.ClientRequests;

import java.util.concurrent.ExecutionException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Ingredients extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    Button barcodeBtn;
    Button manualBtn;
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
        cR = new ClientRequests(getApplicationContext());

        barcodeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Log.d(TAG, "ABUTO TI CAKK");
            // cR.makeReq();
            beginBarcodeScan();
            }
        });

        manualBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Log.d(TAG, "ABUTO TI CAKK");
                String pName = null;
                try {
                    pName = cR.makeReq();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, pName + " us teh pname!!");
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
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        // Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
        zXingScannerView.removeAllViews(); //<- here remove all the views, it will make an Activity having no View
        zXingScannerView.stopCamera(); //<- then stop the camera
        setContentView(R.layout.activity_ingredients); //<- and set the View again.
        tv = (TextView) findViewById(R.id.barcodeID);
        tv.setText(result.getText());
        registerButtonListeners();
        // zXingScannerView.stopCameraPreview();


    }
}
