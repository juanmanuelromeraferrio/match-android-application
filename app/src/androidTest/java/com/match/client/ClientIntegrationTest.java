package com.match.client;

import com.match.client.entities.response.InterestResponse;
import com.match.client.utils.MatchAsyncTask;

import java.util.concurrent.ExecutionException;

import retrofit2.Call;

/**
 * Created by Juan Manuel Romera on 4/5/2016.
 */
public class ClientIntegrationTest {

    private MatchClient getMatchClient() {
        return new MatchClient();
    }

    public void testGetInterests() throws ExecutionException, InterruptedException {
        MatchClient matchClient = getMatchClient();
        Call<InterestResponse> call = matchClient.interests.getInterestResponse();
        MatchAsyncTask<InterestResponse> task = new MatchAsyncTask<>();
        InterestResponse response = task.execute(call).get();
    }
}
