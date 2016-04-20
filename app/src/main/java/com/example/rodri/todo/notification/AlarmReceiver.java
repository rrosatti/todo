package com.example.rodri.todo.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.rodri.todo.R;
import com.example.rodri.todo.database.CategoryTaskDataSource;
import com.example.rodri.todo.task.Task;
import com.example.rodri.todo.ui.activity.NotificationActivity;

import java.sql.SQLException;

/**
 * Created by rodri on 4/17/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private long task_id;
    private Task task;
    private CategoryTaskDataSource dataSource;

    @Override
    public void onReceive(Context context, Intent intent) {

        task_id = intent.getLongExtra("TASK_ID", 0);

        Intent notificationIntent = new Intent(context, NotificationActivity.class);
        notificationIntent.putExtra("TASK_ID", task_id);

        dataSource = new CategoryTaskDataSource(context);

        try {
            dataSource.open();

            task = dataSource.getTask(task_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dataSource.close();

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setContentTitle("Reminder")
                .setContentText("You need to: " + task.getTaskName())
                .setSmallIcon(R.drawable.alarm_notification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true).build();


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

}
