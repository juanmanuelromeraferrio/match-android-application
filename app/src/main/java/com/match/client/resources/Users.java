package com.match.client.resources;

import com.match.client.entities.Token;
import com.match.client.entities.request.UserRequest;
import com.match.client.entities.response.MatchResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Juan Manuel Romera on 16/5/2016.
 */
public interface Users {

    /**
     * Create a User in the system
     * Returns a Token to authenticate the user.
     *
     * @return a {@link Token}
     */
    @POST("user/newuser")
    Call<MatchResponse> createUser(@Body UserRequest user);

    @PUT("user/updateuser")
    Call<MatchResponse> updateUser(@Body UserRequest user);
}
