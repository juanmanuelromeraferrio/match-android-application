package com.match.client.resources;

import com.match.client.entities.Token;
import com.match.client.entities.request.LoginRequest;
import com.match.client.entities.request.UserRequest;
import com.match.client.entities.response.CandidatesResponse;
import com.match.client.entities.response.MatchResponse;
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
public interface Candidates {


    @GET("candidates")
    Call<CandidatesResponse> findCandidates(@Query("id") String id);

}
