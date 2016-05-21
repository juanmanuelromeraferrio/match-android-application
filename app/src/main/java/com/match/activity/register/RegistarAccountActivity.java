package com.match.activity.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.match.R;
import com.match.utils.Validator;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegistarAccountActivity extends AppCompatActivity implements RegisterAccountView {

    @InjectView(R.id.input_name)
    EditText _nameText;
    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_signup)
    Button _signupButton;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private ProgressDialog progressDialog;
    private RegisterAccountController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_sign_up_account_info));

        controller = new RegisterAccountControllerImpl(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    public void signUp() {

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        clearErrors();
        controller.registerUser(name, email, password);
    }

    private void clearErrors() {
        this._nameText.setError(null);
        this._emailText.setError(null);
        this._passwordText.setError(null);
    }

    @Override
    public void setNameError() {
        this._nameText.setError(getBaseContext().getResources().getString(R.string.error_user_name, Validator.MIN_LENGTH_USER_NAME));
    }

    @Override
    public void setEmailError() {
        this._emailText.setError(getBaseContext().getResources().getString(R.string.error_mail));
    }

    @Override
    public void setPasswordError() {
        this._passwordText.setError(getBaseContext().getResources().getString(R.string.error_password, Validator.MIN_LENGTH_PASSWORD, Validator.MAX_LENGTH_PASSWORD));
    }

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getBaseContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(RegistarAccountActivity.this, "", getResources().getString(R.string.creating_user), true, false);
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
        Intent intent = new Intent(RegistarAccountActivity.this, RegisterUserActivity.class);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
