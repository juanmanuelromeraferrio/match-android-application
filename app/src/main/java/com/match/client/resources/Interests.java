package com.match.client.resources;

import com.match.client.entities.response.InterestResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Juan Manuel Romera on 4/5/2016.
 */
public interface Interests {


    /**
     * Get the interest's list
     * Returns a list of all previously created interests.
     *
     * @return a list of {@link Interests}
     */
    @GET("/interests/")
    Call<InterestResponse> getInterestResponse();

}
