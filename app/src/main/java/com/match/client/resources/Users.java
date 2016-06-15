package com.match.client.resources;

import com.match.client.entities.Token;
import com.match.client.entities.request.LoginRequest;
import com.match.client.entities.request.UserRequest;
import com.match.client.entities.response.CandidatesResponse;
import com.match.client.entities.response.MatchResponse;
import com.match.client.entities.response.PhotoResponse;
import com.match.client.entities.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

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

    @PUT("login_user")
    Call<UserResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("user/photo")
    Call<PhotoResponse> getPhoto(@Query("id") String id);
}
