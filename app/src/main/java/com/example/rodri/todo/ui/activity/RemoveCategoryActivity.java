package com.example.rodri.todo.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.rodri.todo.R;
import com.example.rodri.todo.utils.Util;

/**
 * Created by rodri on 4/21/2016.
 */
public class RemoveCategoryActivity  extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_category);
    }

}
