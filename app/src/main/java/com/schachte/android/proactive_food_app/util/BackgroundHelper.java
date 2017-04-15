package com.schachte.android.proactive_food_app.util;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by schachte on 4/14/17.
 */


public class BackgroundHelper  {

    Context mContext;

    public BackgroundHelper(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
