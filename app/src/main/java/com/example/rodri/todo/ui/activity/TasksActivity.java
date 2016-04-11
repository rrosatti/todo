package com.example.rodri.todo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.example.rodri.todo.R;

/**
 * Created by rodri on 4/11/2016.
 */
public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long category_id = extras.getLong("CATEGORY_ID");
        }


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
                startActivity(newTask);
                finish();
                break;
        }

        return true;
    }

}
