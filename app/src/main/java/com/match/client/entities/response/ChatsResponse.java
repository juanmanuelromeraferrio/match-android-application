package com.match.client.entities.response;

import com.match.client.entities.Chat;
import com.match.client.entities.ChatMessage;

import java.util.List;

/**
 * Created by psivori on 22/06/2016.
 */
public class ChatsResponse {

    private List<ChatMessage> chat;

    public ChatsResponse(List<ChatMessage> chat){
        this.chat = chat;
    }

    public List<ChatMessage> getChat() {
        return chat;
    }

}
