package com.match.task;

import android.os.AsyncTask;

import com.match.activity.api.BaseController;
import com.match.client.entities.Candidate;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.service.api.CandidatesService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class FindCandidatesTask extends AsyncTask<User, Void, TaskResponse> {


    private final CandidatesService candidatesService;
    private final BaseController controller;

    public FindCandidatesTask(CandidatesService candidatesService, BaseController controller) {
        this.candidatesService = candidatesService;
        this.controller = controller;
    }

    @Override
    protected void onPreExecute() {
        controller.initTask();
    }

    @Override
    protected TaskResponse doInBackground(User... params) {
        User user = params[0];
        List<Candidate> candidates;
        try {
            candidates = candidatesService.findCandidates(user);
        } catch (ServiceException e) {
            return new TaskResponse(e.getMessage());
        }

        return new TaskResponse(candidates);
    }

    @Override
    protected void onPostExecute(TaskResponse response) {
        controller.onResult(response);
        controller.finishTask();
    }
}
