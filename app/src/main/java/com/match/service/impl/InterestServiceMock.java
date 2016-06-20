package com.match.service.impl;

import com.match.infrastructure.Database;
import com.match.service.api.InterestService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Juan Manuel Romera on 21/5/2016.
 */
public class InterestServiceMock extends InterestService {

    Map<String, List<String>> interests = new HashMap<>();

    public InterestServiceMock(Database database) {
        super(database);
    }

    private Map<String, List<String>> createInterests() {
        List<String> bands = new ArrayList<String>();
        bands.add("The Beatles");
        bands.add("The Who");

        List<String> clubs = new ArrayList<String>();
        clubs.add("Independiente");
        clubs.add("Boca Juniors");
        clubs.add("River Plate");
        clubs.add("Racing");

        interests.put("Bandas", bands);
        interests.put("Clubs", clubs);
        return interests;
    }

    @Override
    public Map<String, List<String>> getInterests() {
        if (database.getInterests() != null) {
            return database.getInterests();
        }
        Map<String, List<String>> interests = createInterests();
        database.setInterests(interests);
        return interests;
    }

    @Override
    public Map<String, List<String>> getLocalInterests() {
        return database.getInterests();
    }
}
