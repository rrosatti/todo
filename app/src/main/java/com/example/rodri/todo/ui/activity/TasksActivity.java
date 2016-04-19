package com.example.rodri.todo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.example.rodri.todo.R;
import com.example.rodri.todo.database.CategoryTaskDataSource;
import com.example.rodri.todo.task.Task;
import com.example.rodri.todo.ui.adapter.TaskAdapter;
import com.example.rodri.todo.utils.DateAndTimeUtil;
import com.example.rodri.todo.utils.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by rodri on 4/11/2016.
 */
public class TasksActivity extends AppCompatActivity {

    long category_id;
    private TaskAdapter taskAdapter;
    private CategoryTaskDataSource dataSource;
    private ExpandableListView taskExpListView;
    private LinkedHashMap<String, ArrayList<Task>> groupsAndTasks;
    private List<Task> tasks;
    private Button setNotification;
    String[] groupNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);
        initialize();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category_id = extras.getLong("CATEGORY_ID");
        }

        try {
            dataSource.open();

            tasks = dataSource.getTasksByCategory(category_id);
            groupNames = new String[] { "Today", "Tomorrow", "Upcoming", "Past" };

            groupsAndTasks = DateAndTimeUtil.getHashMapForTasks(groupNames, tasks);

            taskAdapter = new TaskAdapter(TasksActivity.this, groupsAndTasks);
            taskExpListView.setAdapter(taskAdapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        setNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Task> tasks = new ArrayList<Task>();
                for (int i = 0; i < groupsAndTasks.size() - 1; i++) {
                    for (Task task : groupsAndTasks.get(groupNames[i])) {
                        tasks.add(task);
                    }
                }
                Intent showTasksAbleToSetNotification = new Intent(TasksActivity.this, TasksNotificationActivity.class);
                showTasksAbleToSetNotification.putExtra("CATEGORY_ID", category_id);
                startActivity(showTasksAbleToSetNotification);
            }
        });

        dataSource.close();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tasks_menu, menu);
        RelativeLayout menuLayout = (RelativeLayout) menu.findItem(R.id.addTask).getActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.addTask) :
                Intent newTask = new Intent(TasksActivity.this, NewTaskActivity.class);
                newTask.putExtra("CATEGORY_ID", category_id);
                startActivity(newTask);
                finish();
                break;
        }

        return true;
    }

    public void initialize() {
        taskExpListView = (ExpandableListView) findViewById(R.id.tasksExpListView);
        dataSource = new CategoryTaskDataSource(TasksActivity.this);
        groupsAndTasks = new LinkedHashMap<>();
        setNotification = (Button) findViewById(R.id.btSetNotification);

    }

}
