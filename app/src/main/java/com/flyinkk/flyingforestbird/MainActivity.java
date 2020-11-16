package com.flyinkk.flyingforestbird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import static androidx.core.content.ContextCompat.startActivity;

public class MainActivity extends AppCompatActivity {
    private MainView mainView;
    private Point point;
    private Handler handler;
    public static final String SCORE_KEY = "SCORE";
    private final static long INTERVAL = 30;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        point = new Point();
        intent = new Intent(this, EndActivity.class);
        getWindowManager().getDefaultDisplay().getSize(point);
        mainView = new MainView(this, point.x, point.y);
        setContentView(mainView);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mainView.invalidate();
                    }
                });
            }
        }, 0, INTERVAL);
    }

    public void callNewActivity(int score) {
        intent.putExtra(SCORE_KEY, score);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (MainView.IS_RUNNING) {

        } else {
            super.onBackPressed();
        }
    }
}