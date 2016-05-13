package com.match.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.match.R;
import com.match.error.ValidationError;
import com.match.utils.Validator;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    @InjectView(R.id.btn_signup)
    TextView _signUpButton;
    @InjectView(R.id.link_forget_password)
    TextView _forgetPassword;

    private Validator validator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        validator = new Validator(this);

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

        _forgetPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
/*                Intent intent = new Intent(getApplicationContext(), RegistrarDatosCuentaActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);*/
            }
        });
    }


    private void login() {

        if (!validate()) {
            return;
        }

        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "", "Iniciando Sesión...", true, false);

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        //Valid Passwords
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void signUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpAccountInfoActivity.class);
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

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public boolean validate() {
        boolean valid = true;
        valid &= validateEmail();
        valid &= validatePassword();
        return valid;
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
}
