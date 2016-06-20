package com.match.client.entities;

/**
 * Created by Juan Manuel Romera on 27/5/2016.
 */
public enum CandidateVote {
    YES, NO;

    public boolean isYes() {
        return this.equals(CandidateVote.YES);
    }
}
