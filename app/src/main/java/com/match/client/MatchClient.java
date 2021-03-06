package com.match.client;

import com.match.client.resources.Candidates;
import com.match.client.resources.Chat;
import com.match.client.resources.Interests;
import com.match.client.resources.Matches;
import com.match.client.resources.Users;

import retrofit2.Retrofit;

/**
 * Created by Juan Manuel Romera on 4/5/2016.
 * This class works as a proxy to connect API rest
 * service when user is not logged
 */
public class MatchClient {

    public Interests interests;
    public Users users;
    public Candidates candidates;
    public Matches matches;
    public Chat chat;
    private Boolean auth = Boolean.FALSE;
    private Boolean speed = Boolean.FALSE;
    private String token;

    public MatchClient() {
    }

    public void setToken(String token) {
        if (token != null && !token.isEmpty()) {
            this.token = token;
            this.auth = Boolean.TRUE;
        }
    }

    public void setSpeed(Boolean speed) {
        this.speed = speed;
    }

    public void build() {
        Retrofit retrofit = getRetrofit();
        interests = retrofit.create(Interests.class);
        users = retrofit.create(Users.class);
        candidates = retrofit.create(Candidates.class);
        matches = retrofit.create(Matches.class);
        chat = retrofit.create(Chat.class);
    }

    private Retrofit getRetrofit() {
        if (auth) {
            if (speed) {
                return ServiceGenerator.authorizationSpeedRetrofit(token);
            } else {
                return ServiceGenerator.authorizationRetrofit(token);
            }
        } else {
            return ServiceGenerator.defaultRetrofit();
        }
    }
}
