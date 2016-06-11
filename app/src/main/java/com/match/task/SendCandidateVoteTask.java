package com.match.task;

import android.os.AsyncTask;

import com.match.activity.api.BaseController;
import com.match.client.entities.Candidate;
import com.match.client.entities.CandidateVote;
import com.match.client.entities.response.VoteYesResponse;
import com.match.error.service.ServiceException;
import com.match.service.api.CandidatesService;
import com.match.service.api.UserService;
import com.match.task.response.TaskResponse;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class SendCandidateVoteTask extends AsyncTask<Object, Void, TaskResponse> {

    private CandidatesService candidatesService;
    private UserService userService;
    private BaseController controller;

    public SendCandidateVoteTask(UserService userService, CandidatesService candidatesService, BaseController controller) {
        this.userService = userService;
        this.candidatesService = candidatesService;
        this.controller = controller;
    }

    @Override
    protected TaskResponse doInBackground(Object... params) {
        CandidateTaskResponse response = null;
        CandidateVote vote = (CandidateVote) params[0];
        Candidate candidate = (Candidate) params[1];
        String userId = userService.getLocalUser().getId();
        try {
            if (vote.isYes()) {
                response = new CandidateTaskResponse(CandidateTaskState.VOTE_YES);
                Boolean match = candidatesService.voteYes(userId, candidate.getId());
                response.setResponse(new VoteYesResponse(candidate, match));
            } else {
                response = new CandidateTaskResponse(CandidateTaskState.VOTE_NO);
                candidatesService.voteNo(userId, candidate.getId());
            }
        } catch (ServiceException e) {
            response.setError(e.getMessage());
            return response;
        }
        return response;
    }

    @Override
    protected void onPostExecute(TaskResponse response) {
        controller.onResult(response);
    }
}
