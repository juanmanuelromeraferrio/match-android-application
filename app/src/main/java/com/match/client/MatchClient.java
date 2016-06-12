package com.match.client;

import com.match.client.resources.Candidates;
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

    public final Interests interests;
    public final Users users;
    public final Candidates candidates;
    public final Matches matches;


    public MatchClient() {
        Retrofit retrofit = ServiceGenerator.defaultRetrofit();
        this.interests = retrofit.create(Interests.class);
        this.users = retrofit.create(Users.class);
        this.candidates = retrofit.create(Candidates.class);
        this.matches = retrofit.create(Matches.class);
    }
}
