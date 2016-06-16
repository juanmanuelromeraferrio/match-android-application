package com.match.client.entities.response;

/**
 * Created by Juan Manuel Romera on 29/5/2016.
 */
public class MatchResponse {

    private boolean success;
    private String data;

    public MatchResponse(boolean success, String data) {
        this.success = success;
        this.data = data;
    }

    public MatchResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public String getData() {
        return data;
    }
}
