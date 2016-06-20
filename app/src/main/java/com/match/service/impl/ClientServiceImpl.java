package com.match.service.impl;

import com.match.client.MatchClient;
import com.match.infrastructure.Database;
import com.match.service.api.ClientService;
import com.match.utils.Configuration;

import okhttp3.Headers;

/**
 * Created by Juan Manuel Romera on 17/6/2016.
 */
public class ClientServiceImpl implements ClientService {

    private Database database;

    public ClientServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public MatchClient getAuthClient() {
        String token = database.getToken();
        MatchClient matchClient = new MatchClient();
        matchClient.setToken(token);
        matchClient.build();
        return matchClient;
    }

    @Override
    public MatchClient getClient() {
        MatchClient matchClient = new MatchClient();
        matchClient.build();
        return matchClient;
    }

    @Override
    public void saveToken(Headers headers) {
        String token = headers.get(Configuration.TOKEN_RESPONSE);
        if (token != null && !token.isEmpty()) {
            database.setToken(token);
        }
    }
}
