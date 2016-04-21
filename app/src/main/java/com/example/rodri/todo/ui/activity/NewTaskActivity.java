package com.example.rodri.todo.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.todo.R;
import com.example.rodri.todo.database.CategoryTaskDataSource;
import com.example.rodri.todo.utils.Util;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by rodri on 4/11/2016.
 */
public class NewTaskActivity extends Activity {

    private EditText etTaskName;
    private SeekBar prioritySeekBar;
    private TextView numPriority;
    private Button setDueDate;
    private Button confirm;
    private CategoryTaskDataSource dataSource;
    private long dateInMillis;
    private long category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);
        initialize();

        numPriority.setText(String.valueOf(prioritySeekBar.getProgress()));

        prioritySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                numPriority.setText(String.valueOf(progress));
            }
        });

        setDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date d = f.parse(date);
                            dateInMillis = d.getTime();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                };

                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog datePicker = new DatePickerDialog(NewTaskActivity.this, R.style.AppTheme, datePickerListener,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePicker.show();

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String taskName = etTaskName.getText().toString();
                String priority = numPriority.getText().toString();

                if (taskName.equals("")) {
                    Toast.makeText(NewTaskActivity.this, "You need to fill out the task name field.", Toast.LENGTH_LONG).show();
                    return;
                } else if (dateInMillis == 0) {
                    Toast.makeText(NewTaskActivity.this, "You need to set a due date.", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    try {
                        dataSource.open();
                        dataSource.createTask(taskName, Integer.parseInt(priority), String.valueOf(dateInMillis), category_id, 0);
                        Toast.makeText(NewTaskActivity.this, "HEYY!", Toast.LENGTH_LONG).show();

                        dataSource.close();
                        onBackPressed();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent backToTasksList = new Intent(NewTaskActivity.this, TasksActivity.class);
        backToTasksList.putExtra("CATEGORY_ID", category_id);
        startActivity(backToTasksList);
        finish();

    }

    private void initialize() {
        etTaskName = (EditText) findViewById(R.id.etTaskName);
        prioritySeekBar = (SeekBar) findViewById(R.id.prioritySeekBar);
        numPriority = (TextView) findViewById(R.id.txtNumPriority);
        setDueDate = (Button) findViewById(R.id.btSetDueDate);
        confirm = (Button) findViewById(R.id.btConfirmNewTask);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category_id = extras.getLong("CATEGORY_ID");
        }

        dataSource = new CategoryTaskDataSource(NewTaskActivity.this);
        dateInMillis = 0;

    }

}
