package com.match.infrastructure;

import com.match.client.entities.Token;
import com.match.client.entities.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Juan Manuel Romera on 19/5/2016.
 */
public interface Database {

    String getToken();

    User getUser();

    Map<String, List<String>> getInterests();

    void setUser(User user);

    void setToken(String token);

    void setInterests(Map<String, List<String>> interests);

}
