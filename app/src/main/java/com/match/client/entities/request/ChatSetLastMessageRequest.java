package com.match.client.entities.request;

/**
 * Created by pablo on 20/06/16.
 */
public class ChatSetLastMessageRequest {

    private String idFrom;
    private String idTo;
    private String idMessage;

    public ChatSetLastMessageRequest(String idFrom, String idTo, String idMessage) {
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.idMessage = idMessage;
    }
}
