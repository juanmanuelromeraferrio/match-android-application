package com.match.activity.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.match.R;
import com.match.activity.HomeActivity;
import com.match.activity.register.RegistarAccountActivity;
import com.match.activity.register.RegisterUserActivity;
import com.match.utils.UiUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    @InjectView(R.id.btn_signup)
    TextView _signUpButton;

    private ProgressDialog progressDialog;
    private LoginController controller;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        controller = new LoginControllerImpl(this);

        _emailText.setText("chachi@gmail.com");
        _passwordText.setText("1234");

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        _signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void login() {
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        controller.loginUser(email, password);
    }

    private void signUp() {
        Intent intent = new Intent(LoginActivity.this, RegistarAccountActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void setEmailError(String error) {
        this._emailText.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        this._passwordText.setError(error);
    }


    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getBaseContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(LoginActivity.this, "", "Iniciando Sesión...", true, false);
        progressDialog.show();

    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void goToNext() {
        Intent intent = null;
        if (controller.userHasSavedInformation()) {
            intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
        }
        startActivity(intent);
    }

    @Override
    public void sessionExpired() {
        UiUtils.showSessionExpired(this);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
