package com.match.client.entities;

import java.io.Serializable;

public class ChatMessage implements Serializable {

    private String idMsg;
    private String idUser;
    private String content;

    public ChatMessage(String idMsg, String idUser, String content) {
        this.idMsg = idMsg;
        this.idUser = idUser;
        this.content = content;
    }

    public String getIdMsg() {
        return idMsg;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getContent() {
        return content;
    }

}
