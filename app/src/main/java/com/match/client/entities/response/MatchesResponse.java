package com.match.client.entities.response;

import java.util.List;

/**
 * Created by pablo on 11/06/16.
 */
public class MatchesResponse {

    private final List<UserResponse> users;

    public MatchesResponse(List<UserResponse> users) {
        this.users = users;
    }

    public List<UserResponse> getUsers() {
        return users;
    }
}
