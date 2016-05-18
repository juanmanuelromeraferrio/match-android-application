package com.match.error.service;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class ServiceException extends Exception {

    public ServiceException(String localizedMessage) {
        super(localizedMessage);
    }
}
