package com.match.client.entities.request;

/**
 * Created by pablo on 20/06/16.
 */
public class ChatRequest {

    private String idFrom;
    private String idTo;
    private String message;

    public ChatRequest(String idFrom, String idTo, String message) {
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.message = message;
    }
}
