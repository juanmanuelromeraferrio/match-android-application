package com.match.client;

import com.match.client.resources.Interests;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Juan Manuel Romera on 4/5/2016.
 * This class works as a proxy to connect API rest
 * service when user is not logged
 */
public class MatchClient {

    private static final String DEFAULT_API_URL = "https://shared-server-match.herokuapp.com";

    public final Interests interests;

    public MatchClient() {
        final Retrofit retrofit = defaultRetrofit();
        this.interests = retrofit.create(Interests.class);
    }

    private static Retrofit defaultRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(DEFAULT_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
