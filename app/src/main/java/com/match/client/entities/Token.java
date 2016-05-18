package com.match.client.entities;

/**
 * Created by Juan Manuel Romera on 16/5/2016.
 */
public class Token {

    private final String code;
    private final String userID;
    private final String createdAt;

    public Token(String code, String userID, String createdAt) {
        this.code = code;
        this.userID = userID;
        this.createdAt = createdAt;
    }

    public String getCode() {
        return code;
    }

    public String getUserID() {
        return userID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return String.format("Token[%s, %s, %s]", code, userID, createdAt);
    }
}
