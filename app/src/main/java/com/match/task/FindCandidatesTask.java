package com.match.task;

import android.os.AsyncTask;

import com.match.activity.api.BaseController;
import com.match.client.entities.Candidate;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.service.api.CandidatesService;
import com.match.task.response.CandidateTaskResponse;
import com.match.task.response.TaskResponse;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class FindCandidatesTask extends AsyncTask<User, Void, TaskResponse> {


    private final CandidatesService candidatesService;
    private final BaseController controller;
    private Boolean showLoading = Boolean.TRUE;

    public FindCandidatesTask(CandidatesService candidatesService, BaseController controller) {
        this(candidatesService, controller, Boolean.TRUE);
    }

    public FindCandidatesTask(CandidatesService candidatesService, BaseController controller, Boolean showLoading) {
        this.candidatesService = candidatesService;
        this.controller = controller;
        this.showLoading = showLoading;
    }

    @Override
    protected void onPreExecute() {
        if (showLoading) {
            controller.initTask();
        }
    }

    @Override
    protected TaskResponse doInBackground(User... params) {
        CandidateTaskResponse taskResponse = new CandidateTaskResponse(CandidateTaskState.FINDING_CANDIDATES);
        User user = params[0];
        List<Candidate> candidates;
        try {
            candidates = candidatesService.findCandidates(user);
        } catch (ServiceException e) {
            taskResponse.setError(e.getMessage());
            return taskResponse;
        }

        taskResponse.setResponse(candidates);
        return taskResponse;
    }

    @Override
    protected void onPostExecute(TaskResponse response) {
        controller.onResult(response);
        if (showLoading) {
            controller.finishTask();
        }
    }
}
