package com.match.task.response;

import android.content.res.Resources;

import com.match.MatchApplication;
import com.match.R;

/**
 * Created by Juan Manuel Romera on 17/6/2016.
 */
public class CreateUserTaskResponse extends TaskResponse {

    public CreateUserTaskResponse(String error, String email) {
        if (error.trim().equals(getResources().getString(R.string.entidad_existe))) {
            setError(getResources().getString(R.string.usuario_existente, email));
        } else {
            setError(error);
        }
    }

    public CreateUserTaskResponse() {

    }

    private Resources getResources() {
        return MatchApplication.getContext().getResources();
    }
}
