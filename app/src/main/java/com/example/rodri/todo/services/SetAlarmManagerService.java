package com.example.rodri.todo.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by rodri on 4/19/2016.
 */
public class SetAlarmManagerService extends IntentService {

    public SetAlarmManagerService() {
        super("SetAlarmManagerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        /** verify if there's a alarm set for 'today' */
        WakefulBroadcastReceiver.completeWakefulIntent(intent);
    }

}
