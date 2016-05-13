package com.match.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.match.R;
import com.match.client.entities.User;
import com.match.error.ValidationError;
import com.match.utils.Parameters;
import com.match.utils.Validator;
import com.match.utils.WaitForInternet;
import com.match.utils.WaitForInternetCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUpAccountInfoActivity extends AppCompatActivity {

    @InjectView(R.id.input_name)
    EditText _nameText;
    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_signup)
    Button _signupButton;
    @InjectView(R.id.link_login)
    TextView _loginLink;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.validator = new Validator(this);
        WaitForInternetCallback callback = new WaitForInternetCallback(this) {
            public void onConnectionSuccess() {
                setContentView(R.layout.activity_sign_up_account_info);
                ButterKnife.inject(mActivity);

                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_sign_up_account_info));

                _signupButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signUp();
                    }
                });

                _loginLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        };
        WaitForInternet.setCallback(callback);
    }

    public void signUp() {

        if (!validate()) {
            onSignUpFailed();
            return;
        }
        _signupButton.setEnabled(true);
        User user = createUser();

        Intent intent = new Intent(SignUpAccountInfoActivity.this, SignUpUserInfoActivity.class);
        intent.putExtra(Parameters.USER.toString(), user);
        startActivity(intent);
    }

    public void onSignUpFailed() {
        Toast.makeText(getBaseContext(), R.string.error_activity_sign_up, Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        valid &= validateUserName();
        valid &= validateEmail();
        valid &= validatePassword();
        return valid;
    }


    private boolean validateUserName() {
        String userName = _nameText.getText().toString();
        try {
            this.validator.validateUserName(userName);
        } catch (ValidationError validationError) {
            _nameText.setError(validationError.getMessage());
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        String email = _emailText.getText().toString();
        try {
            this.validator.validateEmail(email);
        } catch (ValidationError validationError) {
            _emailText.setError(validationError.getMessage());
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        String password = _passwordText.getText().toString();
        try {
            this.validator.validatePassword(password);
        } catch (ValidationError validationError) {
            _passwordText.setError(validationError.getMessage());
            return false;
        }
        return true;
    }

    private User createUser() {
        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        return new User(name, email, password);
    }


}
