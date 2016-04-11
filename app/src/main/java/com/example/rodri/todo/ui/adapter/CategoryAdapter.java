package com.example.rodri.todo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rodri.todo.R;
import com.example.rodri.todo.category.Category;
import com.example.rodri.todo.database.CategoryTaskDataSource;
import com.example.rodri.todo.task.Task;
import com.example.rodri.todo.ui.activity.TasksActivity;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by rodri on 4/10/2016.
 */
public class CategoryAdapter extends ArrayAdapter<Category> {

    private Activity activity;
    private List<Category> lCategory;
    private static LayoutInflater inflater = null;
    private CategoryTaskDataSource dataSource;

    public CategoryAdapter (Activity activity, int textViewResourceId, List<Category> lCategory) {
        super (activity, textViewResourceId, lCategory);
        try {
            this.activity = activity;
            this.lCategory = lCategory;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return lCategory.size();
    }

    public static class ViewHolder {
        public TextView displayCategoryName;
        public TextView displayNumTasks;
    }

    public View getView (final int position, View convertView, final ViewGroup parent) {
        View v = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                v = inflater.inflate(R.layout.categories_list, null);
                holder = new ViewHolder();

                holder.displayCategoryName = (TextView) v.findViewById(R.id.txtCategoryName);
                holder.displayNumTasks = (TextView) v.findViewById(R.id.txtNumTasks);

                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }

            holder.displayCategoryName.setText(lCategory.get(position).getCategoryName());

            dataSource = new CategoryTaskDataSource(activity);

            try {
                dataSource.open();
                List<Task> tasks = dataSource.getTasksByCategory(lCategory.get(position).getId());
                System.out.println("I've been here?");
                if (tasks.size() > 0) {
                    holder.displayNumTasks.setText(String.valueOf(tasks.size()) + " items");
                } else {
                    holder.displayNumTasks.setText("0 items");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent tasksList = new Intent(activity, TasksActivity.class);
                    tasksList.putExtra("CATEGORY_ID", lCategory.get(position).getId());
                    activity.startActivity(tasksList);
                    activity.finish();

                }
            });


            dataSource.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

}
