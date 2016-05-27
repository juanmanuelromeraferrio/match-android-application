package com.match.fragment.candidates;

import com.match.client.entities.Candidate;
import com.match.client.entities.CandidateVote;
import com.match.client.entities.User;
import com.match.service.api.CandidatesService;
import com.match.service.api.UserService;
import com.match.service.factory.ServiceFactory;
import com.match.task.CandidateTaskResponse;
import com.match.task.FindCandidatesTask;
import com.match.task.SendCandidateVoteTask;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 22/5/2016.
 */
public class CandidatesControllerImpl implements CandidatesController {

    private CandidatesView view;
    private CandidatesService candidatesService;
    private UserService userService;

    public CandidatesControllerImpl(CandidatesView view) {
        this.view = view;
        this.candidatesService = ServiceFactory.getCandidatesService();
        this.userService = ServiceFactory.getUserService();
    }

    @Override
    public void findCandidates() {
        User localUser = this.userService.getLocalUser();
        FindCandidatesTask task = new FindCandidatesTask(candidatesService, this);
        task.execute(localUser);
    }

    @Override
    public void likeCandidate(Candidate candidate) {
        executeSendVoteTask(candidate, CandidateVote.YES);
    }

    @Override
    public void dislikeCandidate(Candidate candidate) {
        executeSendVoteTask(candidate, CandidateVote.NO);
    }

    private void executeSendVoteTask(Candidate candidate, CandidateVote vote) {
        SendCandidateVoteTask sendVoteTask = new SendCandidateVoteTask(userService, candidatesService, this);
        sendVoteTask.execute(vote, candidate.getId());

        User localUser = this.userService.getLocalUser();
        FindCandidatesTask findCandidates = new FindCandidatesTask(candidatesService, this, Boolean.FALSE);
        findCandidates.execute(localUser);
    }

    @Override
    public void initTask() {
        this.view.showProgress();
        this.view.clearCandidates();
    }

    @Override
    public void finishTask() {
        this.view.hideProgress();
        this.view.finishLoadingCandidates();
    }


    @Override
    public void onResult(Object result) {
        CandidateTaskResponse response = (CandidateTaskResponse) result;
        switch (response.getState()) {
            case FINDING_CANDIDATES:
                findCandidatesResult(response);
                break;
            case VOTE_YES:
                sendVoteYesResult(response);
                break;
            case VOTE_NO:
                sendVoteNoResult();
                break;
        }

    }

    private void findCandidatesResult(CandidateTaskResponse response) {
        if (response.hasError()) {
            this.view.onError(response.getError());
        } else {
            List<Candidate> candidates = (List<Candidate>) response.getResponse();
            if (!candidates.isEmpty()) {
                this.view.addCandidates(candidates);
            }
        }
    }

    private void sendVoteYesResult(CandidateTaskResponse response) {
        Boolean wasMatch = (Boolean) response.getResponse();
        if (wasMatch != null) {
            this.view.showMatch();
        }
        this.view.removeCurrentCandidate();
    }

    private void sendVoteNoResult() {
        this.view.removeCurrentCandidate();
    }


}
