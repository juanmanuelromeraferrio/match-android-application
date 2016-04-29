package com.match.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.match.R;
import com.match.listener.DatabaseOperationListener;

import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements DatabaseOperationListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.match.utils.WaitForInternetCallback callback = new com.match.utils.WaitForInternetCallback(this) {
            public void onConnectionSuccess() {
                setContentView(R.layout.activity_login);
                ButterKnife.inject(mActivity);
            }
        };
        com.match.utils.WaitForInternet.setCallback(callback);
    }

    public void onApplicationLoginClick(View v) {
        Intent intent = new Intent(LoginActivity.this, ApplicationLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    @Override
    public void onOperationSuccess() {
/*        Intent intent = new Intent(LoginActivity.this, RegistrarDatosPersonalesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
