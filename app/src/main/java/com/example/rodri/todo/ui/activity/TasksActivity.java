package com.example.rodri.todo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.example.rodri.todo.R;
import com.example.rodri.todo.database.CategoryTaskDataSource;
import com.example.rodri.todo.task.Task;
import com.example.rodri.todo.ui.adapter.TaskAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rodri on 4/11/2016.
 */
public class TasksActivity extends AppCompatActivity {

    long category_id;
    private TaskAdapter taskAdapter;
    private CategoryTaskDataSource dataSource;
    private ExpandableListView taskExpListView;
    private HashMap<String, ArrayList<Task>> groupsAndTasks;
    private List<Task> tasks;
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

            tasks = dataSource.getAllTasks();
            groupNames = new String[] { "Today", "Tomorrow", "Upcoming" };
            for (int i = 0; i < groupNames.length; i++) {
                System.out.println("group: " + groupNames[i]);
                groupsAndTasks.put(groupNames[i], new ArrayList<Task>());
            }

            long tomorrowInMillis = 86400000;

            for (Task task : tasks) {
                if (Long.parseLong(task.getDueDate()) == System.currentTimeMillis() ) {
                    groupsAndTasks.get(groupNames[0]).add(task);
                } else
                    if (Long.parseLong(task.getDueDate()) == (System.currentTimeMillis() + tomorrowInMillis)) {
                        groupsAndTasks.get(groupNames[1]).add(task);
                    } else {
                        groupsAndTasks.get(groupNames[2]).add(task);
                    }
            }

            taskAdapter = new TaskAdapter(TasksActivity.this, groupsAndTasks);
            taskExpListView.setAdapter(taskAdapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        groupsAndTasks = new HashMap<>();

    }

}
