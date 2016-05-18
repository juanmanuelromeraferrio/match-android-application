package com.match.error.service;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class APIError {

    private boolean success;
    private String data;

    public APIError() {
    }

    public boolean isSuccess() {
        return success;
    }

    public String getData() {
        return data;
    }
}
