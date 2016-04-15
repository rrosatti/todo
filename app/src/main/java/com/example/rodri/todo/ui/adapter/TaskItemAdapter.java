package com.example.rodri.todo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rodri.todo.R;
import com.example.rodri.todo.task.Task;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by rodri on 4/15/2016.
 */
public class TaskItemAdapter extends ArrayAdapter<Task> {

    private Activity activity;
    private ArrayList<Task> tasks;
    private static LayoutInflater inflater = null;

    public TaskItemAdapter (Activity activity, int textViewResourceId, ArrayList<Task> tasks) {
        super (activity, textViewResourceId, tasks);
        try {
            this.activity = activity;
            this.tasks = tasks;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    public static class ViewHolder {
        public TextView displayTaskName;
        public TextView displayDueDate;
    }

    @Override
    public View getView (final int pos, View convertView, ViewGroup parent) {
        View v = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                v = inflater.inflate(R.layout.custom_item, null);
                holder = new ViewHolder();

                holder.displayTaskName = (TextView) v.findViewById(R.id.txtTaskName);
                holder.displayDueDate = (TextView) v.findViewById(R.id.txtDueDate);

                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }

            holder.displayTaskName.setText(tasks.get(pos).getTaskName());

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(Long.parseLong(tasks.get(pos).getDueDate()));

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String date = day + "/" + month + "/" + year;
            holder.displayDueDate.setText(date);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

}
