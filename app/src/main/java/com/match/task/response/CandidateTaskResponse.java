package com.match.task.response;

import com.match.task.CandidateTaskState;
import com.match.task.response.TaskResponse;

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
