package com.example.rodri.todo.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rodri.todo.R;
import com.example.rodri.todo.database.CategoryTaskDataSource;
import com.example.rodri.todo.task.Task;

import java.sql.SQLException;

/**
 * Created by rodri on 4/17/2016.
 */
public class NotificationActivity extends Activity {

    private long taskId;
    private TextView showNotification;
    private CategoryTaskDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        initialize();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskId = extras.getInt("TASK_ID");
        }

        try {
            dataSource.open();

            Task task = dataSource.getTask(taskId);

            showNotification.setText("Remember -> " + task.getTaskName());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dataSource.close();


    }

    public void initialize() {
        showNotification = (TextView) findViewById(R.id.txtNotification);
        dataSource = new CategoryTaskDataSource(NotificationActivity.this);
    }

}
