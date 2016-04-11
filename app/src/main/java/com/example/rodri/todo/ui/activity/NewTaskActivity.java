package com.example.rodri.todo.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.todo.R;
import com.example.rodri.todo.util.Util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by rodri on 4/11/2016.
 */
public class NewTaskActivity extends Activity {

    private SeekBar prioritySeekBar;
    private TextView numPriority;
    private Button setDueDate;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Util.setFullScreen(NewTaskActivity.this);
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
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        Toast.makeText(NewTaskActivity.this, "The chosen date is: " + date, Toast.LENGTH_LONG).show();
                    }
                };

                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog datePicker = new DatePickerDialog(NewTaskActivity.this, R.style.AppTheme, datePickerListener,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePicker.show();

            }
        });

    }

    private void initialize() {
        prioritySeekBar = (SeekBar) findViewById(R.id.prioritySeekBar);
        numPriority = (TextView) findViewById(R.id.txtNumPriority);
        setDueDate = (Button) findViewById(R.id.btSetDueDate);
        confirm = (Button) findViewById(R.id.btConfirm);
    }

}
