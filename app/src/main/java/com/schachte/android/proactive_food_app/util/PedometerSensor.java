package com.schachte.android.proactive_food_app.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.widget.Toast;

import com.schachte.android.proactive_food_app.database.DataAccessLayer;

import static android.content.Context.SENSOR_SERVICE;

public class PedometerSensor implements SensorEventListener {

    private SensorManager sensorManager;
    public Handler handler = null;
    public static Runnable runnable = null;
    public Context mContext;
    public int steps;
    public long MILLISECOND_DELAY = 30000;
    DataAccessLayer dal;


    public PedometerSensor(Context context){
        mContext = context;
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);

            handler = new Handler();
            runnable = new Runnable() {
                public void run() {
                    Toast.makeText(mContext, "Service is still running!! : " + Integer.toString(steps), Toast.LENGTH_LONG).show();
                    addNewPedometerLog(steps);
                    handler.postDelayed(runnable, MILLISECOND_DELAY);
                }
            };
            handler.postDelayed(runnable, MILLISECOND_DELAY);

        } else {
            Toast.makeText(context, "Count sensor not available on device.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //Update currently tracked step count value
        steps = (int) event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void addNewPedometerLog(int stepCount){
        if (dal == null) {
            dal = new DataAccessLayer(mContext);
            dal.getPedometerLogs();
        }


        //TODO: Get current steps and steps since reset.

        //TODO:
    }
}