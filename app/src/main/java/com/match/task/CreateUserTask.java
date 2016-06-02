package com.match.task;

import android.os.AsyncTask;

import com.match.activity.api.BaseController;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.service.api.InterestService;
import com.match.service.api.UserService;
import com.match.task.response.TaskResponse;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class CreateUserTask extends AsyncTask<User, Void, TaskResponse> {

    private static final boolean REMOTE_INTERESTS = false;
    private UserService userService;
    private InterestService interestService;
    private BaseController controller;


    public CreateUserTask(UserService userService, InterestService interestService, BaseController controller) {
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
    protected TaskResponse doInBackground(User... params) {
        User user = params[0];
        try {
            userService.createUser(user);
            interestService.getInterests();
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
