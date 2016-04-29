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

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Juan Manuel Romera on 28/04/2016.
 */
public class ApplicationLoginActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    @InjectView(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_login);
        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
/*                Intent intent = new Intent(getApplicationContext(), RegistrarDatosCuentaActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);*/
            }
        });
    }

    public void login() {

        if (!validate()) {
            return;
        }

        final ProgressDialog progressDialog = ProgressDialog.show(ApplicationLoginActivity.this, "", "Iniciando Sesión...", true, false);

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        //Valid Passwords
        Intent intent = new Intent(ApplicationLoginActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

        String username = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        String errorEmail = getResources().getString(R.string.error_email_login);
        String errorPassword = getResources().getString(R.string.error_password_login);

        if (username.isEmpty()) {
            _emailText.setError(errorEmail);
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError(errorPassword);
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
