package com.match.service.impl;

import com.match.infrastructure.Database;
import com.match.service.api.InterestService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Juan Manuel Romera on 29/5/2016.
 */
public class InterestServiceImpl extends InterestService {

    public InterestServiceImpl(Database database) {
        super(database);
    }

    @Override
    public Map<String, List<String>> getInterests(boolean local) {
        return new HashMap<>();
    }
}
