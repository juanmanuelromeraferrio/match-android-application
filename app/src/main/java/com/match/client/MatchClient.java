package com.match.client;

import com.match.client.resources.Interests;
import com.match.client.resources.Users;
import com.match.utils.Configuration;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Juan Manuel Romera on 4/5/2016.
 * This class works as a proxy to connect API rest
 * service when user is not logged
 */
public class MatchClient {

    public final Interests interests;
    public final Users users;


    public MatchClient() {
        this.interests = ServiceGenerator.createDefaultService(Interests.class);
        this.users = ServiceGenerator.createDefaultService(Users.class);
    }
}
