package com.match.service.api;

import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;

import java.util.List;
import java.util.Map;

/**
 * Created by Juan Manuel Romera on 21/5/2016.
 */
public abstract class InterestService implements MatchService {

    protected Database database;

    protected InterestService(Database database) {
        this.database = database;
    }

    public abstract Map<String, List<String>> getInterests(boolean local);

    public ServiceType getType() {
        return ServiceType.INTEREST;
    }

}

