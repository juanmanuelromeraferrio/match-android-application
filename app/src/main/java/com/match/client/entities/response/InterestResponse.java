package com.match.client.entities.response;

import com.match.client.entities.Interest;
import com.match.client.entities.Metadata;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 4/5/2016.
 */
public class InterestResponse {

    private final List<Interest> interests;
    private final Metadata metadata;

    public InterestResponse(List<Interest> interests, Metadata metadata) {
        this.interests = interests;
        this.metadata = metadata;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public Metadata getMetadata() {
        return metadata;
    }
}
