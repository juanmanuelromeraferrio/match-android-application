package com.match.fragment.candidates;

import com.match.client.entities.Candidate;
import com.match.client.entities.User;
import com.match.service.api.CandidatesService;
import com.match.service.factory.ServiceFactory;
import com.match.task.FindCandidatesTask;
import com.match.task.TaskResponse;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 22/5/2016.
 */
public class CandidatesControllerImpl implements CandidatesController {

    private CandidatesView view;
    private CandidatesService candidatesService;

    public CandidatesControllerImpl(CandidatesView view) {
        this.view = view;
        this.candidatesService = ServiceFactory.getCandidatesService();
    }

    @Override
    public void findCandidates() {
        User localUser = ServiceFactory.getUserService().getLocalUser();
        FindCandidatesTask task = new FindCandidatesTask(candidatesService, this);
        task.execute(localUser);
    }

    @Override
    public void likeCandidate(Candidate user) {
        this.view.removeCurrentCandidate();
    }

    @Override
    public void dislikeCandidate(Candidate user) {
        this.view.removeCurrentCandidate();
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
        TaskResponse response = (TaskResponse) result;
        if (response.hasError()) {
            this.view.onError(response.getError());
        } else {
            List<Candidate> candidates = (List<Candidate>) response.getResponse();
            if (!candidates.isEmpty()) {
                this.view.addCandidates(candidates);
            }
        }
    }
}
