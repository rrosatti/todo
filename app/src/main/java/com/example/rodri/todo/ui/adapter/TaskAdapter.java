package com.example.rodri.todo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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
public class TaskAdapter extends BaseAdapter {

    private Activity activity;
    private LinkedHashMap<String, ArrayList<Task>> items;
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

    public int getCount() {
        return items.size();
    }

    public Task getItem(int pos) {
        return items.get(groupPos[0]).get(pos);
    }

    public long getItemId(int pos) {
        return pos;
    }

    public class ViewHolder {
        public TextView displaySectionName;
        public ListView taskBySection;
        public LinearLayout linearLayout;

        public TextView displayTaskName;
        public TextView displayDueDate;
    }

    @Override
    public View getView(final int pos, View convertView, final ViewGroup parent) {

        View v = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                v = inflater.inflate(R.layout.custom_task_item, null);
                holder = new ViewHolder();

                holder.displaySectionName = (TextView) v.findViewById(R.id.txtSectionName);
                //holder.taskBySection = (ListView) v.findViewById(R.id.tasksBySectionListView);
                holder.linearLayout = (LinearLayout) v.findViewById(R.id.linearLayout);

                v.setTag(holder);

            } else {
                holder = (ViewHolder) v.getTag();
            }

            holder.displaySectionName.setText(groupPos[pos]);

            ArrayList<Task> lTasks = items.get(groupPos[pos]);

            System.out.println("pos[" + pos + "] name: " + groupPos[pos] + " tasks.size() " + lTasks.size());
            if (lTasks.size() > 0 ) {
                for (Task task : lTasks) {
                    System.out.println("task - " + task.getTaskName());
                    View v2 = inflater.inflate(R.layout.custom_item, null);
                    holder.displayTaskName = (TextView) v2.findViewById(R.id.txtTaskName);
                    holder.displayDueDate = (TextView) v2.findViewById(R.id.txtDueDate);

                    holder.displayTaskName.setText(task.getTaskName());

                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(Long.parseLong(task.getDueDate()));

                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH) + 1;
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    String date = day + "/" + month + "/" + year;

                    holder.displayDueDate.setText(date);

                    holder.linearLayout.addView(v2);
                }
            }

            //TaskItemAdapter taskItemAdapter = new TaskItemAdapter(activity, 0, items.get(groupPos[pos]));

            //holder.taskBySection.setAdapter(taskItemAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    /**

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

    */

}
