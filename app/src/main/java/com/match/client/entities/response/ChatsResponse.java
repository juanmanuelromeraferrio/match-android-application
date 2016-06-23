package com.match.client.entities.response;

import com.match.client.entities.Chat;
import com.match.client.entities.ChatMessage;

import java.util.List;

/**
 * Created by psivori on 22/06/2016.
 */
public class ChatsResponse {

    private List<ChatResponse> chat;

    public ChatsResponse(List<ChatResponse> chat) {
        this.chat = chat;
    }

    public List<ChatResponse> getChat() {
        return chat;
    }

}
