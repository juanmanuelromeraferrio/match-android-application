package com.match.client.resources;

import com.match.client.entities.Token;
import com.match.client.entities.User;
import com.match.client.entities.request.UserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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
    @POST("/users/")
    Call<Token> createUser(@Body UserRequest user);

    @PUT("/users/{id}/")
    void updateUser(@Path("id") String id, @Body User user);
}
