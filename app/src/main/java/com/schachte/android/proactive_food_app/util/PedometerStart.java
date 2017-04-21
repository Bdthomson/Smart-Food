package com.schachte.android.proactive_food_app.util;

import android.app.Service;
import android.os.IBinder;
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
        PedometerSensor pedSensor = new PedometerSensor(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}