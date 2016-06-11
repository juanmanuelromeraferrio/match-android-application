package com.match.client.entities.request;

/**
 * Created by Juan Manuel Romera on 11/6/2016.
 */
public class VoteRequest {

    private String idFrom;
    private String idTo;

    public VoteRequest(String idFrom, String idTo) {
        this.idFrom = idFrom;
        this.idTo = idTo;
    }
}
