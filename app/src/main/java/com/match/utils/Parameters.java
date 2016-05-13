package com.match.utils;

/**
 * Created by Juan Manuel Romera on 12/5/2016.
 */
public enum Parameters {

    USER("user");

    private String value;

    private Parameters(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
