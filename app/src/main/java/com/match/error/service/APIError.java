package com.match.error.service;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class APIError {

    private boolean success;
    private String data;
    private boolean sessionExpired;

    public APIError() {
    }

    public APIError(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSessionExpired() {
        return sessionExpired;
    }

    public void setSessionExpired(boolean sessionExpired) {
        this.sessionExpired = sessionExpired;
    }
}
