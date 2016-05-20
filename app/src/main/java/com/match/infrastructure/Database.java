package com.match.infrastructure;

import com.match.client.entities.Token;
import com.match.client.entities.User;

/**
 * Created by Juan Manuel Romera on 19/5/2016.
 */
public interface Database {

    Token getToken();

    User getUser();

    void setUser(User user);

    void setToken(Token token);
}
