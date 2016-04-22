package com.example.rodri.todo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.rodri.todo.R;
import com.example.rodri.todo.category.Category;
import com.example.rodri.todo.database.CategoryTaskDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 4/21/2016.
 */
public class RemoveCategoryAdapter extends ArrayAdapter<Category> {


    private Activity activity;
    private CategoryTaskDataSource dataSource;
    private LayoutInflater inflater = null;
    private List<Long> checkedItems;
    private List<Category> categories;

    public RemoveCategoryAdapter(Activity activity, int textViewResourceId, List<Category> categories) {
        super(activity, textViewResourceId, categories);
        try {
            this.activity = activity;
            this.categories = categories;
            this.checkedItems = new ArrayList<>();

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return categories.size();
    }

    public class ViewHolder {
        public TextView displayCategoryName;
        public CheckBox checkBox;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.custom_remove_category_item, null);

            holder.displayCategoryName = (TextView) v.findViewById(R.id.txtCategoryNameToBeRemoved);
            holder.checkBox = (CheckBox) v.findViewById(R.id.removeCategoryCheckBox);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.displayCategoryName.setText(categories.get(position).getCategoryName());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((CheckBox)v).isChecked()) {
                    checkedItems.add(categories.get(position).getId());
                    categories.get(position).setChecked(true);
                } else {
                    checkedItems.remove(categories.get(position).getId());
                    categories.get(position).setChecked(false);
                }

            }
        });

        return v;
    }



}
