package com.match.client.entities.response;

import com.match.client.entities.User;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class UserResponse {

    private User user;

    public UserResponse(User user) {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }
}
