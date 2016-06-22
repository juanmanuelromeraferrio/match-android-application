package com.match.client.resources;

import com.match.client.entities.request.ChatPullRequest;
import com.match.client.entities.request.ChatRequest;
import com.match.client.entities.request.ChatSetLastMessageRequest;
import com.match.client.entities.response.ChatResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by pablo on 20/06/16.
 */
public interface Chat {

    @POST("chat/message")
    Call<ChatResponse> sendMessage(@Body ChatRequest request);

    @GET("chat/message")
    Call<ChatResponse> pullHistory(@Query("idFrom")String idFrom,@Query("idTo") String idTo);

    @GET("chat/new")
    Call<ChatResponse> pullNewMessages(@Query("idFrom")String idFrom,@Query("idTo") String idTo);

    @PUT("chat/last")
    Call<ChatResponse> setLastMessage(@Body ChatSetLastMessageRequest request);
}
