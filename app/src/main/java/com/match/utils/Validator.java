package com.match.utils;

import android.content.Context;

/**
 * Created by Juan Manuel Romera on 12/5/2016.
 */
public class Validator {

    public static final int MIN_LENGTH_USER_NAME = 3;
    public static final int MIN_LENGTH_PASSWORD = 4;
    public static final int MAX_LENGTH_PASSWORD = 10;

    private final Context context;

    public Validator(Context context) {
        this.context = context;
    }

    public boolean isUserNameValid(String userName) {
        if (userName.isEmpty() || userName.length() < MIN_LENGTH_USER_NAME) {
            return false;
        }
        return true;
    }

    public boolean isEmailValid(String email) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        return true;
    }

    public boolean isPasswordValid(String password) {
        if (password.isEmpty() || password.length() < MIN_LENGTH_PASSWORD || password.length() > MAX_LENGTH_PASSWORD) {
            return false;
        }
        return true;
    }

    public boolean isAgeValid(String age) {
        if (age == null && age.isEmpty()) {
            return false;
        }

        int ageInteger = new Integer(age);
        if (ageInteger >= 18 && ageInteger <= 100) {
            return true;
        } else {
            return false;
        }
    }
}
