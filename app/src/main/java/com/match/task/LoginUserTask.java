package com.match.task;

import android.os.AsyncTask;

import com.match.activity.api.BaseController;
import com.match.client.entities.Candidate;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.service.api.InterestService;
import com.match.service.api.UserMatchesService;
import com.match.service.api.UserService;
import com.match.task.response.LoginTaskResponse;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class LoginUserTask extends AsyncTask<String, Void, LoginTaskResponse> {

    private static final boolean REMOTE_INTERESTS = false;

    private UserService userService;
    private InterestService interestService;
    private UserMatchesService userMatchesService;
    private BaseController controller;

    public LoginUserTask(UserService userService, UserMatchesService userMatchesService, InterestService interestService, BaseController controller) {
        this.userService = userService;
        this.interestService = interestService;
        this.userMatchesService = userMatchesService;
        this.controller = controller;
    }

    @Override
    protected void onPreExecute() {
        if (controller != null)
            controller.initTask();
    }

    @Override
    protected LoginTaskResponse doInBackground(String... params) {
        String email = params[0];
        String password = params[1];

        try {
            userService.loginUser(email, password);
            User user = userService.getLocalUser();
            List<Candidate> userMatches = userMatchesService.findUserMatches(user);
            user.setUserMatches(userMatches);
            userService.saveUser(user);

            if (!userService.hasSavedInformation()) {
                interestService.getInterests();
            }

        } catch (ServiceException e) {
            return new LoginTaskResponse(e.getMessage());
        }

        return new LoginTaskResponse();
    }

    @Override
    protected void onPostExecute(LoginTaskResponse response) {
        if (controller != null)
            controller.finishTask();

        controller.onResult(response);
    }
}
