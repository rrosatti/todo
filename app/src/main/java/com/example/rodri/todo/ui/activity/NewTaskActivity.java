package com.example.rodri.todo.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.rodri.todo.R;

/**
 * Created by rodri on 4/11/2016.
 */
public class NewTaskActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);

        SeekBar prioritySeekBar = (SeekBar) findViewById(R.id.prioritySeekBar);
        final TextView numPriority = (TextView) findViewById(R.id.txtNumPriority);
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

    }

}
