package com.match.client.entities;

import java.io.Serializable;

public class ChatMessage implements Serializable {

    private String id;
    private String user;
    private String content;

    public ChatMessage(String id, String user, String content) {
        this.id = id;
        this.user = user;
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setContent(String content) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage that = (ChatMessage) o;

        if (!id.equals(that.id)) return false;
        if (!user.equals(that.user)) return false;
        return content.equals(that.content);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + content.hashCode();
        return result;
    }
}
