package com.match.client.entities.request;

/**
 * Created by pablo on 20/06/16.
 */
public class ChatSetLastMessageRequest {

    private String idFrom;
    private String idTo;
    private String idMsg;

    public ChatSetLastMessageRequest(String idFrom, String idTo, String idMsg) {
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.idMsg = idMsg;
    }
}
