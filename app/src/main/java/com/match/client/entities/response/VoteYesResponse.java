package com.match.client.entities.response;

import com.match.client.entities.Candidate;

/**
 * Created by Juan Manuel Romera on 11/6/2016.
 */
public class VoteYesResponse {

    private Candidate candidate;
    private Boolean match;

    public VoteYesResponse(Candidate candidate, Boolean match) {
        this.candidate = candidate;
        this.match = match;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public Boolean getMatch() {
        return match;
    }
}
