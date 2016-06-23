package com.match.client.entities.response;

import com.match.client.entities.ChatMessage;

/**
 * Created by pablo on 20/06/16.
 */
public class ChatResponse {

    private ChatMessage message;

    public ChatResponse(ChatMessage message) {
        this.message = message;
    }

    public ChatMessage getMessage() {
        return message;
    }
}
