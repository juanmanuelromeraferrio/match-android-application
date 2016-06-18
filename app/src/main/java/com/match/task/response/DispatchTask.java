package com.match.task.response;

import android.os.AsyncTask;

import com.match.activity.HomeActivity;
import com.match.activity.api.BaseController;
import com.match.activity.login.LoginActivity;
import com.match.error.service.ServiceException;
import com.match.service.api.UserService;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class DispatchTask extends AsyncTask<Void, Void, TaskResponse> {


    private final UserService userService;
    private final BaseController controller;

    public DispatchTask(UserService userService, BaseController controller) {
        this.userService = userService;
        this.controller = controller;
    }


    @Override
    protected void onPreExecute() {
    }

    @Override
    protected TaskResponse doInBackground(Void... params) {
        TaskResponse taskResponse = new TaskResponse();
        try {
            if (userService.isUserLogged()) {
                if (userService.hasSavedInformation()) {
                    taskResponse.setResponse(HomeActivity.class);
                } else {
                    taskResponse.setResponse(LoginActivity.class);
                }
            } else {
                taskResponse.setResponse(LoginActivity.class);
            }
        } catch (ServiceException e) {
            taskResponse.setError(e.getMessage());
        }

        return taskResponse;
    }

    @Override
    protected void onPostExecute(TaskResponse response) {
        controller.onResult(response);
    }
}
