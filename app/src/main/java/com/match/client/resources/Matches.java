package com.match.client.resources;

import com.match.client.entities.request.MatchRequest;
import com.match.client.entities.response.CandidatesResponse;
import com.match.client.entities.response.MatchResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by pablo on 11/06/16.
 */
public interface Matches {

    @GET("chats")
    Call<CandidatesResponse> findMatches(@Query("id") String id);

    /**
     * @param idFrom
     * @param idTo
     * @return
     */
    @FormUrlEncoded
    @POST("/match/confirm")
    Call<MatchResponse> acceptMatch(@Field("idFrom") String idFrom,@Field("idTo") String idTo);
}
