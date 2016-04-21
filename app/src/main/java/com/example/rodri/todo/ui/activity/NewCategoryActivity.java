package com.example.rodri.todo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodri.todo.R;
import com.example.rodri.todo.database.CategoryTaskDataSource;
import com.example.rodri.todo.utils.Util;

import java.sql.SQLException;

/**
 * Created by rodri on 4/10/2016.
 */
public class NewCategoryActivity extends Activity {

    private CategoryTaskDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_category);

        dataSource = new CategoryTaskDataSource(this);

        final EditText etCategoryName = (EditText) findViewById(R.id.etCategoryName);
        Button confirm = (Button) findViewById(R.id.btConfirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String categoryName = etCategoryName.getText().toString();

                if (categoryName.equals("")) {
                    Toast.makeText(NewCategoryActivity.this, "You need to write the category name!", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    try {
                        dataSource.open();
                        dataSource.createCategory(categoryName);

                        Intent backToMainActivity = new Intent(NewCategoryActivity.this, MainActivity.class);
                        startActivity(backToMainActivity);
                        finish();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent backToMainActivity = new Intent(NewCategoryActivity.this, MainActivity.class);
        startActivity(backToMainActivity);
        finish();
    }
}
