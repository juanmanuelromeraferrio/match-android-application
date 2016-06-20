package com.match.fragment.candidates;

import android.os.AsyncTask;

import com.match.client.entities.Candidate;
import com.match.client.entities.CandidateVote;
import com.match.client.entities.User;
import com.match.client.entities.response.VoteYesResponse;
import com.match.service.api.CandidatesService;
import com.match.service.api.UserMatchesService;
import com.match.service.api.UserService;
import com.match.service.factory.ServiceFactory;
import com.match.task.FindCandidatesMatchTask;
import com.match.task.FindCandidatesTask;
import com.match.task.GetPhotoTask;
import com.match.task.SendCandidateAcceptMatchTask;
import com.match.task.SendCandidateVoteTask;
import com.match.task.response.CandidateTaskResponse;
import com.match.task.response.PhotoTaskResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Manuel Romera on 22/5/2016.
 */
public class CandidatesControllerImpl implements CandidatesController {

    private CandidatesView view;
    private CandidatesService candidatesService;
    private UserMatchesService userMatchesService;
    private UserService userService;

    private boolean isFindingCandidates = Boolean.FALSE;

    public CandidatesControllerImpl(CandidatesView view) {
        this.view = view;
        this.candidatesService = ServiceFactory.getCandidatesService();
        this.userMatchesService = ServiceFactory.getUserMatchesService();
        this.userService = ServiceFactory.getUserService();
    }

    @Override
    public void findCandidates() {
        if (!isFindingCandidates) {
            isFindingCandidates = Boolean.TRUE;
            User localUser = this.userService.getLocalUser();
            FindCandidatesTask task = new FindCandidatesTask(candidatesService, this);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, localUser);
        }
    }

    @Override
    public void likeCandidate(Candidate candidate) {
        executeSendVoteTask(candidate, CandidateVote.YES);
    }

    @Override
    public void dislikeCandidate(Candidate candidate) {
        executeSendVoteTask(candidate, CandidateVote.NO);
    }

    @Override
    public void acceptMatch(Candidate candidate) {
        executeSendAcceptMatch(candidate);
    }

    private void executeSendAcceptMatch(Candidate candidate) {
        SendCandidateAcceptMatchTask sendCandidateAcceptMatchTask = new SendCandidateAcceptMatchTask(userMatchesService,this);
        sendCandidateAcceptMatchTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userService.getLocalUser(),candidate);
    }

    @Override
    public void findCandidatesMatch(){
        FindCandidatesMatchTask task = new FindCandidatesMatchTask(userMatchesService,this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, userService.getLocalUser());
    }

    public void getPhoto(Candidate candidate) {
        GetPhotoTask task = new GetPhotoTask(candidatesService, this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, candidate.getId());
    }

    @Override
    public boolean isFindingCandidates() {
        return isFindingCandidates;
    }

    private void executeSendVoteTask(Candidate candidate, CandidateVote vote) {
        SendCandidateVoteTask sendVoteTask = new SendCandidateVoteTask(userService, candidatesService, this);
        sendVoteTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, vote, candidate);
        view.removeCurrentCandidate();
    }

    @Override
    public void initTask() {
        this.view.showProgress();
    }

    @Override
    public void finishTask() {
        this.view.hideProgress();
    }


    @Override
    public void onResult(Object result) {
        CandidateTaskResponse response = (CandidateTaskResponse) result;
        switch (response.getState()) {
            case FINDING_CANDIDATES:
                isFindingCandidates = Boolean.FALSE;
                findCandidatesResult(response);
                break;
            case VOTE_YES:
                sendVoteYesResult(response);
                break;
            case GET_PHOTO:
                loadPhoto(response);
                break;
            case MATCHED:
                acceptMatchResult(response);
                break;
            case LIST_MATCHS:
                listMatchResult(response);
                break;

        }

    }

    private void listMatchResult(CandidateTaskResponse response) {
        if (response.sessionExpired()) {
            view.sessionExpired();
        } else if (response.hasError()) {
            this.view.onError(response.getError());
        }else{
            List<Candidate> listMatches = (List<Candidate>) response.getResponse();
            this.view.addCandidatesMatches(listMatches);
        }
    }

    private void acceptMatchResult(CandidateTaskResponse response) {
        if (response.sessionExpired()) {
            view.sessionExpired();
        } else if (response.hasError()) {
            this.view.onError(response.getError());
        } else{
            List<Candidate> candidates = new ArrayList<>();
            candidates.add((Candidate)response.getResponse());
            this.view.addCandidatesMatches(candidates);
        }
    }

    private void findCandidatesResult(CandidateTaskResponse response) {
        if (response.sessionExpired()) {
            view.sessionExpired();
        } else if (response.hasError()) {
            this.view.onError(response.getError());
        } else {
            List<Candidate> candidates = (List<Candidate>) response.getResponse();
            this.view.addCandidates(candidates);
        }
    }

    private void sendVoteYesResult(CandidateTaskResponse response) {
        if (response.sessionExpired()) {
            view.sessionExpired();
        } else {
            VoteYesResponse responseResponse = (VoteYesResponse) response.getResponse();
            if (responseResponse.getMatch() != null && responseResponse.getMatch()) {
                this.view.showMatch(responseResponse.getCandidate());
            }
        }
    }

    private void loadPhoto(CandidateTaskResponse response) {
        if (response.sessionExpired()) {
            view.sessionExpired();
        } else {
            PhotoTaskResponse photoTaskResponse = (PhotoTaskResponse) response.getResponse();
            this.view.loadPhoto(photoTaskResponse.getIdCandidatePhoto(), photoTaskResponse.getPhoto());
        }

    }


}
