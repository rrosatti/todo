package com.example.rodri.todo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.rodri.todo.R;
import com.example.rodri.todo.database.CategoryTaskDataSource;
import com.example.rodri.todo.task.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by rodri on 4/13/2016.
 */
public class TaskAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private HashMap<String, ArrayList<Task>> items;
    private static LayoutInflater inflater = null;
    private CategoryTaskDataSource dataSource;
    private String[] groupPos;

    public TaskAdapter (Activity activity, LinkedHashMap<String, ArrayList<Task>> items) {
        this.activity = activity;
        this.items = items;
        this.groupPos = new String[items.size()];
        int i = 0;
        for (String key : items.keySet()) {
            groupPos[i] = key;
            i++;
        }

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Task getChild(int groupPosition, int childPosition) {
        List<Task> taskList = items.get(groupPos[groupPosition]);
        return taskList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public class ViewHolder {
        public TextView displayTaskName;
        public TextView displayDueDate;
        public TextView displayGroupName;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        Task task = getChild(groupPosition, childPosition);
        if (convertView == null) {

            v = inflater.inflate(R.layout.custom_item, null);

            holder.displayTaskName = (TextView) v.findViewById(R.id.txtTaskName);
            holder.displayDueDate = (TextView) v.findViewById(R.id.txtDueDate);

            v.setTag(holder);

        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.displayTaskName.setText(task.getTaskName());

        //Just testing git branch
        if (groupPosition == 3) {
            //holder.displayTaskName.setTextAppearance(activity, R.style.pastTask);
            //holder.displayDueDate.setTextAppearance(activity, R.style.pastTask);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(task.getDueDate()));

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String date = day + "/" + month + "/" + year;
        holder.displayDueDate.setText(date);


        return v;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<Task> taskList = items.get(groupPos[groupPosition]);
        return taskList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return items.get(groupPos[groupPosition]);
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView (int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View v = convertView;
        if (convertView == null) {
            v = inflater.inflate(R.layout.custom_group, null);


            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }


        holder.displayGroupName = (TextView) v.findViewById(R.id.txtGroupName);
        holder.displayGroupName.setText(groupPos[groupPosition]);


        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
