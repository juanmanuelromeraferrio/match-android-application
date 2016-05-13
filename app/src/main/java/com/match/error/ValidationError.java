package com.match.error;

/**
 * Created by Juan Manuel Romera on 12/5/2016.
 */
public class ValidationError extends Exception {

    public ValidationError(String message) {
        super(message);
    }
}
