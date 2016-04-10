package com.example.rodri.todo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.rodri.todo.R;
import com.example.rodri.todo.category.Category;
import com.example.rodri.todo.database.CategoryTaskDataSource;
import com.example.rodri.todo.ui.adapter.CategoryAdapter;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CategoryTaskDataSource dataSource;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView categoriesList = (GridView) findViewById(R.id.categoriesGridView);

        dataSource = new CategoryTaskDataSource(MainActivity.this);
        try {
            dataSource.open();
            List<Category> categories = dataSource.getCategories();
            categoryAdapter = new CategoryAdapter(MainActivity.this, 0, categories);

            categoriesList.setAdapter(categoryAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dataSource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        RelativeLayout menuLayout = (RelativeLayout) menu.findItem(R.id.addCategory).getActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.addCategory:
                Intent newCategory = new Intent(MainActivity.this, NewCategoryActivity.class);
                startActivity(newCategory);
                finish();
                break;

        }
        return true;
    }
}
