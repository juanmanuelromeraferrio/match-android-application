package com.match.task;

import android.os.AsyncTask;

import com.match.activity.api.BaseController;
import com.match.client.entities.Candidate;
import com.match.client.entities.Chat;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.service.api.UserMatchesService;
import com.match.task.response.TaskResponse;

import java.util.List;

/**
 * Created by pablo on 20/06/16.
 */
public class FindChatsTask extends AsyncTask<User, Void, TaskResponse> {

    private final UserMatchesService userMatchesService;
    private final BaseController controller;

    public FindChatsTask(UserMatchesService userMatchesService, BaseController controller) {
        this.userMatchesService = userMatchesService;
        this.controller = controller;
    }


    @Override
    protected void onPreExecute() {
        if (controller != null)
            controller.initTask();
    }

    @Override
    protected TaskResponse doInBackground(User... params) {
        TaskResponse response = new TaskResponse();
        User loggedUser = (User) params[0];

        try {
            List<Chat> listCandidatesMatches = userMatchesService.findUserMatches(loggedUser);
            response.setResponse(listCandidatesMatches);
        } catch (ServiceException e) {
            response.setError(e.getMessage());
            response.setSessionExpired(e.isSessionExpired());
            return response;
        }
        return response;
    }

    @Override
    protected void onPostExecute(TaskResponse response) {
        controller.onResult(response);

        if (controller != null)
            controller.finishTask();
    }
}
