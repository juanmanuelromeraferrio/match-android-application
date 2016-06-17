package com.match.service.api;

import com.match.client.MatchClient;

import okhttp3.Headers;
import okhttp3.Response;
import retrofit2.http.HEAD;

/**
 * Created by Juan Manuel Romera on 17/6/2016.
 */
public interface ClientService {

    MatchClient getAuthClient();

    MatchClient getClient();

    void saveToken(Headers headers);

}
