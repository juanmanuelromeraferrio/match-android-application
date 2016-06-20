package com.match.client.entities.response;

import com.match.client.entities.Interest;
import com.match.client.entities.Metadata;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 4/5/2016.
 */
public class InterestResponse {

    private final List<Interest> interests;

    public InterestResponse(List<Interest> interests) {
        this.interests = interests;
    }

    public List<Interest> getInterests() {
        return interests;
    }
}
