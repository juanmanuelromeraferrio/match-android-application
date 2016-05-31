package com.match.client.entities.request;

import com.match.client.entities.User;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class UserRequest {

    private User user;

    public UserRequest(User user) {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }
}
