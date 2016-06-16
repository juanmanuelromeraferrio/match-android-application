package com.match.client.resources;

import com.match.client.entities.request.MatchRequest;
import com.match.client.entities.response.CandidatesResponse;
import com.match.client.entities.response.MatchResponse;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by pablo on 11/06/16.
 */
public interface Matches {

    @GET("chats")
    Call<CandidatesResponse> findMatches(@Query("id") String id);

    @POST("match/confirm")
    Call<MatchResponse> acceptMatch(@Body MatchRequest matchRequest);
}
