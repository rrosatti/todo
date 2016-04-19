package com.example.rodri.todo.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.example.rodri.todo.services.SetAlarmManagerService;

/**
 * Created by rodri on 4/19/2016.
 */
public class BootBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startIntentService = new Intent(context, SetAlarmManagerService.class);
        startWakefulService(context, startIntentService);
    }

}
