package com.match.task;

import android.os.AsyncTask;

import com.match.activity.api.BaseController;
import com.match.client.entities.Candidate;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.service.api.UserMatchesService;
import com.match.task.response.CandidateTaskResponse;
import com.match.task.response.TaskResponse;

/**
 * Created by pablo on 19/06/16.
 */
public class SendCandidateAcceptMatchTask extends AsyncTask<Object, Void, TaskResponse> {

    private UserMatchesService userMatchesService;
    private BaseController controller;

    public SendCandidateAcceptMatchTask(UserMatchesService userMatchesService, BaseController controller){
        this.userMatchesService = userMatchesService;
        this.controller = controller;
    }


    @Override
    protected TaskResponse doInBackground(Object... params) {
        CandidateTaskResponse response = new CandidateTaskResponse(CandidateTaskState.MATCHED);
        User loggedUser = (User) params[0];
        Candidate candidate = (Candidate) params[1];

        try {
               userMatchesService.acceptMatch(loggedUser,candidate);
               response.setResponse(candidate);
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
    }

}
