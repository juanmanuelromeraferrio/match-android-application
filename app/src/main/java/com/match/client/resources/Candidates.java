package com.match.client.resources;

import com.match.client.entities.request.VoteRequest;
import com.match.client.entities.response.CandidatesResponse;
import com.match.client.entities.response.MatchResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Juan Manuel Romera on 16/5/2016.
 */
public interface Candidates {


    @GET("candidates")
    Call<CandidatesResponse> findCandidates(@Query("id") String id);

    @POST("match/yes")
    Call<MatchResponse> voteYes(@Body VoteRequest voteRequest);

    @POST("match/no")
    Call<MatchResponse> voteNo(@Body VoteRequest voteRequest);


}
