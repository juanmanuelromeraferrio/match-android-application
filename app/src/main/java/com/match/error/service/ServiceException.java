package com.match.error.service;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class ServiceException extends Exception {

    private APIError error;

    public ServiceException(String messagge) {
        super(messagge);
    }

    public ServiceException(APIError error) {
        super(error.getData());
        this.error = error;
    }

    public boolean isSessionExpired() {
        if (error != null) {
            return error.isSessionExpired();
        }
        return Boolean.FALSE;
    }
}
