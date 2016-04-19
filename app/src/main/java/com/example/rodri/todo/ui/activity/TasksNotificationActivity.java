package com.example.rodri.todo.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.rodri.todo.R;
import com.example.rodri.todo.database.CategoryTaskDataSource;
import com.example.rodri.todo.task.Task;
import com.example.rodri.todo.ui.adapter.TaskNotificationAdapter;
import com.example.rodri.todo.utils.DateAndTimeUtil;
import com.example.rodri.todo.utils.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by rodri on 4/16/2016.
 */
public class TasksNotificationActivity extends Activity {

    private ListView tasksNotification;
    private CategoryTaskDataSource dataSource;
    private TaskNotificationAdapter adapter;
    private LinkedHashMap<String, ArrayList<Task>> groupAndTasks;
    private ArrayList<Task> listOfTasks;
    private long category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_notification);
        initialize();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category_id = extras.getLong("CATEGORY_ID");
        }

        try {
            dataSource.open();

            List<Task> temp = dataSource.getTasksByCategory(category_id);
            String[] groupNames = new String[] { "Today", "Tomorrow", "Upcoming", "Past" };
            groupAndTasks = DateAndTimeUtil.getHashMapForTasks(groupNames, temp);

            for (int i = 0; i < groupAndTasks.size() - 1; i++) {
                for (Task task : groupAndTasks.get(groupNames[i])) {
                    System.out.println(task.getTaskName());
                    listOfTasks.add(task);
                }
            }

            adapter = new TaskNotificationAdapter(TasksNotificationActivity.this, 0, listOfTasks);
            tasksNotification.setAdapter(adapter);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void initialize() {
        tasksNotification = (ListView) findViewById(R.id.tasksNotificationListView);
        dataSource = new CategoryTaskDataSource(TasksNotificationActivity.this);
        groupAndTasks = new LinkedHashMap<>();
        listOfTasks = new ArrayList<>();
    }
}
