package com.example.rodri.todo.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

import com.example.rodri.todo.alarm.Alarm;
import com.example.rodri.todo.database.CategoryTaskDataSource;
import com.example.rodri.todo.utils.AlarmManagerUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 4/19/2016.
 */
public class SetAlarmManagerService extends Service {

    private CategoryTaskDataSource dataSource;


    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {

        /** verify if there's a alarm set for 'today' */
        Toast.makeText(getApplicationContext(), "I've been here (Service)", Toast.LENGTH_LONG).show();
        try {
            dataSource = new CategoryTaskDataSource(getApplicationContext());
            dataSource.open();

            boolean verify = dataSource.isThereAnyAlarm();

            if (verify) {
                List<Alarm> alarms = dataSource.getAllAlarms();

                for (Alarm alarm : alarms) {
                    long time = Long.parseLong(alarm.getAlarmTime());
                    long task_id = alarm.getTaskId();
                    AlarmManagerUtil.setAlarm(getApplicationContext(), time, task_id);
                }

            }


            //WakefulBroadcastReceiver.completeWakefulIntent(intent);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
