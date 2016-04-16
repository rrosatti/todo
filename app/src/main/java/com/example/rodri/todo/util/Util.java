package com.example.rodri.todo.util;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by rodri on 4/11/2016.
 */
public class Util {

    public static void setFullScreen(Activity activity) {

        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public static long getTomorrowInMillis() {
        long tomorrow = 0;
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, 1);
        String tomorrowString = cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
        try {
            Date time2 = new SimpleDateFormat("dd-MM-yyyy").parse(tomorrowString);
            tomorrow = time2.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tomorrow;

    }

    public static long getTodayInMillis() {
        long today = 0;
        Calendar cal = new GregorianCalendar();
        String todayString = cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
        try {
            Date time = new SimpleDateFormat("dd-MM-yyyy").parse(todayString);
            today = time.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return today;
    }

    public static String convertDateInMillisToString(long dateInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateInMillis);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String date = day + "/" + month + "/" + year;

        return date;
    }

}
