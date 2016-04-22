package com.example.rodri.todo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rodri.todo.R;
import com.example.rodri.todo.category.Category;
import com.example.rodri.todo.database.CategoryTaskDataSource;
import com.example.rodri.todo.ui.adapter.RemoveCategoryAdapter;
import com.example.rodri.todo.utils.Util;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by rodri on 4/21/2016.
 */
public class RemoveCategoryActivity  extends Activity{

    private ListView categoriesToBeRemoved;
    private Button removeCategories;
    private CategoryTaskDataSource dataSource;
    private RemoveCategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_category);
        initialize();

        List<Category> categories;
        try {
            dataSource.open();

            categories = dataSource.getCategories();
            adapter = new RemoveCategoryAdapter(this, 0, categories);
            categoriesToBeRemoved.setAdapter(adapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }



        removeCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    Category category = adapter.getItem(i);
                    if (category.isChecked()) {
                        dataSource.deleteCategory(category);
                    }
                }
                Intent backToCategoriesMenu = new Intent(RemoveCategoryActivity.this, MainActivity.class);
                startActivity(backToCategoriesMenu);
                finish();
            }
        });

    }

    public void initialize() {
        categoriesToBeRemoved = (ListView) findViewById(R.id.removeCategoryListView);
        dataSource = new CategoryTaskDataSource(this);
        removeCategories = (Button) findViewById(R.id.btRemoveCategory);
    }

}
