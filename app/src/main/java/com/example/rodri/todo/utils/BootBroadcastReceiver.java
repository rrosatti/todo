package com.example.rodri.todo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

import com.example.rodri.todo.services.SetAlarmManagerService;

/**
 * Created by rodri on 4/19/2016.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startIntentService = new Intent(context, SetAlarmManagerService.class);
        context.startService(startIntentService);
    }

}
