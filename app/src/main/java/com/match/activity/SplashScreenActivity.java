package com.match.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import com.match.*;


public class SplashScreenActivity extends AppCompatActivity implements DispatchView {

    private static final long SPLASH_SCREEN_DELAY = 1200;
    private DispatchView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        view = this;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                DispatchController controller = new DispatchControllerImpl(view);
                controller.dispatch();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }


    @Override
    public void goToNext(Class<?> nextActivityClass) {
        Intent intent = new Intent(this, nextActivityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
