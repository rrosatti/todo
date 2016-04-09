package com.example.rodri.todo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;

import com.example.rodri.todo.R;

/**
 * Created by rodri on 4/9/2016.
 */
public class SplashScreenActivity extends Activity{

    private boolean _active = true;
    private int _splashTime = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        /** Get width and height of phone screen */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        ImageView img = (ImageView) findViewById(R.id.todoImageView);
        img.setImageResource(R.drawable.todo_2);
        img.getLayoutParams().width = (int) (0.70 * width);
        img.getLayoutParams().height = (int) (0.45 * height);
        img.requestLayout();

        final Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) waited += 100;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                    Intent startApp = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(startApp);
                }
            }
        };
        splashThread.start();
    }


}
