package com.match.client.entities.response;

import com.match.client.entities.Interest;
import com.match.client.entities.User;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 2/6/2016.
 */
public class CandidatesResponse {

    private final List<UserResponse> users;

    public CandidatesResponse(List<UserResponse> users) {
        this.users = users;
    }

    public List<UserResponse> getUsers() {
        return users;
    }
}
