package com.match.task;

/**
 * Created by Juan Manuel Romera on 27/5/2016.
 */
public class CandidateTaskResponse extends TaskResponse {

    private CandidateTaskState state;

    public CandidateTaskResponse(CandidateTaskState state) {
        this.state = state;
    }

    public CandidateTaskState getState() {
        return state;
    }
}
