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
import com.example.rodri.todo.util.Util;

import java.util.ArrayList;

/**
 * Created by rodri on 4/16/2016.
 */
public class TaskNotificationAdapter extends ArrayAdapter<Task> {

    private Activity activity;
    private LayoutInflater inflater = null;
    private ArrayList<Task> tasks;

    public TaskNotificationAdapter (Activity activity, int textResourceViewId, ArrayList<Task> tasks) {
        super (activity, textResourceViewId, tasks);
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

    public class ViewHolder {
        private TextView displayTaskName;
        private TextView displayDueDate;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.custom_item, null);

            holder.displayTaskName = (TextView) v.findViewById(R.id.txtTaskName);
            holder.displayDueDate = (TextView) v.findViewById(R.id.txtDueDate);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.displayTaskName.setText(tasks.get(position).getTaskName());
        String date = Util.convertDateInMillisToString(Long.parseLong(tasks.get(position).getDueDate()));
        holder.displayDueDate.setText(String.valueOf(date));

        return v;
    }

}
