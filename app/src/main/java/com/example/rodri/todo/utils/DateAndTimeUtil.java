package com.example.rodri.todo.utils;

import com.example.rodri.todo.task.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by rodri on 4/19/2016.
 */
public class DateAndTimeUtil {

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

    public static LinkedHashMap<String, ArrayList<Task>> getHashMapForTasks(String [] keys, List<Task> tasks) {
        LinkedHashMap<String, ArrayList<Task>> groupsAndTasks = new LinkedHashMap<>();
        long today = 0, tomorrow = 0;

        for (int i = 0; i < keys.length; i++) {
            groupsAndTasks.put(keys[i], new ArrayList<Task>());
        }

        today = DateAndTimeUtil.getTodayInMillis();
        tomorrow = DateAndTimeUtil.getTomorrowInMillis();

        for (Task task : tasks) {
            long dueDate = Long.parseLong(task.getDueDate());
            if ( dueDate == today ) {
                groupsAndTasks.get(keys[0]).add(task);
            } else
            if (dueDate == tomorrow) {
                groupsAndTasks.get(keys[1]).add(task);
            } else if (dueDate > tomorrow){
                groupsAndTasks.get(keys[2]).add(task);
            } else {
                groupsAndTasks.get(keys[3]).add(task);
            }
        }

        return groupsAndTasks;

    }


}
