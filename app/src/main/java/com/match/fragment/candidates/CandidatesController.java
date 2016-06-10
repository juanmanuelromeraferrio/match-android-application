package com.match.fragment.candidates;

import com.match.activity.api.BaseController;
import com.match.client.entities.Candidate;

/**
 * Created by Juan Manuel Romera on 22/5/2016.
 */
public interface CandidatesController extends BaseController {

    void findCandidates();

    void likeCandidate(Candidate candidate);

    void dislikeCandidate(Candidate candidate);

    void acceptMatch(String candidateId);

    void getPhoto(Candidate candidate);

    boolean isFindingCandidates();

}
