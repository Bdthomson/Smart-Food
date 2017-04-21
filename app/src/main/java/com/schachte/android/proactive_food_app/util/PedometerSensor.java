package com.schachte.android.proactive_food_app.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.schachte.android.proactive_food_app.database.DataAccessLayer;
import com.schachte.android.proactive_food_app.models.PedometerEntry;

import java.security.acl.LastOwnerException;

import static android.content.Context.SENSOR_SERVICE;

public class PedometerSensor implements SensorEventListener {

    private SensorManager sensorManager;
    public Handler handler = null;
    public static Runnable runnable = null;
    public Context mContext;
    public float steps = -1;
    public long MILLISECOND_DELAY = 5000;
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
                    Toast.makeText(mContext, "Service is still running!! : " + Float.toString(steps), Toast.LENGTH_LONG).show();
                    if (steps != -1)
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
        steps = event.values[0];

        Log.d("HomeActivity", "Sensor Changed");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * TABLE SCHEMA

     KEY_TOTAL_STEPS | STEPS_SINCE_RESET

     1000 | 1000 // Sensor value - 1000
     5000 | 5000 // Sensor value - 5000

     PHONE RESET

     7000  | 2000 // Sensor value - 2000
     8000  | 3000 // Sensor value - 3000
     10000 | 5000 // Sensor value - 5000

     PHONE RESET

     11000 | 1000 // Sensor value - 1000
     */

    public void addNewPedometerLog(float stepCount) {

        if (dal == null) {
            dal = new DataAccessLayer(mContext);
        }

        dal.getAllPedometerEntries();

        PedometerEntry lastEntry = dal.getLastPedometerEntry();

        // Only ran on the first insertion in the DB
        if (lastEntry == null) {

            // USE CASE: User installs app, has moved since opening app.
            // If there is no data and the step count has triggered.
            if (stepCount != 0.0) {
                dal.insertPedometerLog(stepCount, stepCount);
            }

        // USE CASE: User has already ran the application before.
        } else {

            if (stepCount < lastEntry.getStepsSinceReset()) {
                dal.insertPedometerLog(lastEntry.getTotalSteps() + stepCount, stepCount);
                Log.d("HomeActivity", "// Handling Phone Reboot");

            // Handle
            } else {
                dal.insertPedometerLog(lastEntry.getTotalSteps() + stepCount - lastEntry.getStepsSinceReset(), stepCount);
                Log.d("HomeActivity", "// Handling Normal Insertion");
            }
        }
    }
}