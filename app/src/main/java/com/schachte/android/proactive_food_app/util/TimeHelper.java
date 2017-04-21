package com.schachte.android.proactive_food_app.util;

/**
 * Created by schachte on 4/20/17.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeHelper {
    public static String getDateNow() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2016/11/16
        return dateFormat.format(date);
    }

    public static String getDateTimeNow() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        return dateFormat.format(date);
    }

    public static String getTimeNow() {

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //12:08:43
        return dateFormat.format(date);
    }
}
