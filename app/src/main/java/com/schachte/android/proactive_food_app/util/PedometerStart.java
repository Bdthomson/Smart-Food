package com.schachte.android.proactive_food_app.util;

import android.app.Service;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;

public class PedometerStart extends Service {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Pedometer Logging Started", Toast.LENGTH_LONG).show();
        Log.d("HomeActivity", "working!!!!!");
        PedometerSensor pedSensor = new PedometerSensor(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Pedometer Logging Killed", Toast.LENGTH_LONG).show();
    }
}