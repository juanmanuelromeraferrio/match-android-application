package com.match.task.response;

import com.match.MatchApplication;
import com.match.R;

/**
 * Created by Juan Manuel Romera on 2/6/2016.
 */
public class LoginTaskResponse extends TaskResponse {

    private String userError;
    private String passwordError;

    public LoginTaskResponse(String error) {
        if (error.trim().equals(getString(R.string.entidad_no_existente))) {
            userError = getString(R.string.usuario_no_existe);
        } else if (error.trim().equals(getString(R.string.password_invalido_server))) {
            passwordError = getString(R.string.password_invalido);
        } else {
            setError(error);
        }
    }

    public LoginTaskResponse() {

    }

    public Boolean hasUserError() {
        return userError != null;
    }

    public Boolean hasPasswordError() {
        return passwordError != null;
    }

    public String getUserError() {
        return userError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    private String getString(int id) {
        return MatchApplication.getContext().getResources().getString(id);
    }
}
