package com.match.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.match.activity.api.BaseController;
import com.match.client.entities.Interest;
import com.match.client.entities.Location;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.service.api.InterestService;
import com.match.service.api.UserService;
import com.match.utils.PhotoUtils;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class LoginUserTask extends AsyncTask<String, Void, TaskResponse> {

    private static final boolean REMOTE_INTERESTS = false;

    private UserService userService;
    private InterestService interestService;
    private BaseController controller;

    public LoginUserTask(UserService userService, InterestService interestService, BaseController controller) {
        this.userService = userService;
        this.interestService = interestService;
        this.controller = controller;
    }

    @Override
    protected void onPreExecute() {
        if (controller != null)
            controller.initTask();
    }

    @Override
    protected TaskResponse doInBackground(String... params) {
        String email = params[0];
        String password = params[1];

        try {
            userService.loginUser(email, password);
            if (!userService.hasSavedInformation()) {
                interestService.getInterests(REMOTE_INTERESTS);
            }
        } catch (ServiceException e) {
            return new TaskResponse(e.getMessage());
        }

        return new TaskResponse();
    }

    @Override
    protected void onPostExecute(TaskResponse response) {
        if (controller != null)
            controller.finishTask();

        controller.onResult(response);
    }
}
