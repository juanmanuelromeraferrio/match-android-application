package com.match.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Juan Manuel Romera on 18/5/2016.
 */
public class DispatchActivity extends AppCompatActivity implements DispatchView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DispatchController controller = new DispatchControllerImpl(this);
        controller.dispatch();
    }

    @Override
    public void goToNext(Class<?> nextActivityClass) {
        startActivity(new Intent(this, nextActivityClass));
    }
}
