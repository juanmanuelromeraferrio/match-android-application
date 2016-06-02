package com.match.client.entities.response;

import com.match.client.entities.Interest;
import com.match.client.entities.User;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 2/6/2016.
 */
public class CandidatesResponse {

    private final List<User> users;

    public CandidatesResponse(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
