package com.match.client.entities.response;

import com.match.client.entities.ChatMessage;

/**
 * Created by pablo on 20/06/16.
 */
public class ChatResponse {

    private String id;
    private String user;
    private String content;

    public ChatResponse(String id, String user, String content) {
        this.id = id;
        this.user = user;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }
    /*  private ChatMessage chat;

    public ChatResponse(ChatMessage chat){
        this.chat = chat;
    }

    public ChatMessage getChat(){
        return this.chat;
    }
 */
}
