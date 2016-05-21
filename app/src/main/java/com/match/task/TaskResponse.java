package com.match.task;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class TaskResponse {

    private String error;

    public TaskResponse(String error) {
        this.error = error;
    }

    public TaskResponse() {
        this.error = "";
    }

    public boolean hasError() {
        return !error.isEmpty();
    }

    public String getError() {
        return error;
    }
}
