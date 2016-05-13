package com.match.utils;

import android.content.Context;

import com.match.R;
import com.match.error.ValidationError;

/**
 * Created by Juan Manuel Romera on 12/5/2016.
 */
public class Validator {

    private static final int MIN_LENGTH_USER_NAME = 3;
    private static final int MIN_LENGTH_PASSWORD = 4;
    private static final int MAX_LENGTH_PASSWORD = 10;

    private final Context context;

    public Validator(Context context) {
        this.context = context;
    }

    public void validateUserName(String userName) throws ValidationError {
        if (userName.isEmpty() || userName.length() < MIN_LENGTH_USER_NAME) {
            String errorMsg = context.getResources().getString(R.string.error_user_name, MIN_LENGTH_USER_NAME);
            throw new ValidationError(errorMsg);
        }
    }

    public void validateEmail(String email) throws ValidationError {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            String errorMsg = context.getResources().getString(R.string.error_mail);
            throw new ValidationError(errorMsg);
        }
    }

    public void validatePassword(String password) throws ValidationError {
        if (password.isEmpty() || password.length() < MIN_LENGTH_PASSWORD || password.length() > MAX_LENGTH_PASSWORD) {
            String errorMsg = context.getResources().getString(R.string.error_password, MIN_LENGTH_PASSWORD, MAX_LENGTH_PASSWORD);
            throw new ValidationError(errorMsg);
        }
    }
}
