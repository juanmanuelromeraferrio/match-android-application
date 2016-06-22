package com.match.client.entities.request;

/**
 * Created by pablo on 21/06/16.
 */
public class ChatPullRequest {

    private String idFrom;
    private String idTo;


    public ChatPullRequest(String idFrom, String idTo) {
        this.idFrom = idFrom;
        this.idTo = idTo;
    }
}
