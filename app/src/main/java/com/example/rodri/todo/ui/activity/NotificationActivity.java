package com.example.rodri.todo.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rodri.todo.R;
import com.example.rodri.todo.alarm.Alarm;
import com.example.rodri.todo.database.CategoryTaskDataSource;
import com.example.rodri.todo.task.Task;
import com.example.rodri.todo.utils.DateAndTimeUtil;
import com.example.rodri.todo.utils.Util;

import java.sql.SQLException;

/**
 * Created by rodri on 4/17/2016.
 */
public class NotificationActivity extends Activity {

    private long taskId;
    private TextView showNotificationTask;
    private TextView showNotificationPriority;
    private TextView showNotificationDate;
    private CategoryTaskDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        initialize();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskId = extras.getLong("TASK_ID");
        }

        try {
            dataSource.open();

            Task task = dataSource.getTask(taskId);

            showNotificationTask.setText(task.getTaskName());
            showNotificationPriority.setText("Priority: " + task.getPriority());
            showNotificationDate.setText(DateAndTimeUtil.convertDateInMillisToString(Long.parseLong(task.getDueDate())));

            Alarm alarm = dataSource.getAlarmByTaskID(taskId);
            System.out.println("alarm id: " + alarm.getId());
            dataSource.deleteAlarm(alarm);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dataSource.close();


    }

    public void initialize() {
        showNotificationTask = (TextView) findViewById(R.id.txtNotificationTask);
        showNotificationPriority = (TextView) findViewById(R.id.txtNotificationPriority);
        showNotificationDate = (TextView) findViewById(R.id.txtNotificationDate);
        dataSource = new CategoryTaskDataSource(NotificationActivity.this);
    }

}
