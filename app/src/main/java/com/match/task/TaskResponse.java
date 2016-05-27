package com.match.task;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class TaskResponse {

    private String error;
    private Object response;

    public TaskResponse(String error) {
        this.error = error;
    }

    public TaskResponse(Object response) {
        this.response = response;
        this.error = "";
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

    public Object getResponse() {
        return response;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
