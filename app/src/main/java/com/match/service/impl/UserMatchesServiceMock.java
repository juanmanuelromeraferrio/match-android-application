package com.match.service.impl;

import com.match.client.entities.Candidate;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;
import com.match.service.api.UserMatchesService;
import com.match.utils.mapper.CandidateMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Manuel Romera on 21/6/2016.
 */
public class UserMatchesServiceMock extends UserMatchesService {


    public UserMatchesServiceMock(Database database, CandidateMapper mapper) {
        super(database, mapper);
    }

    @Override
    public List<Candidate> findUserMatches(User user) throws ServiceException {
        //String id, String name, String age, Bitmap photo, String interests
        Candidate erica = new Candidate("1", "Erica Atanasoff", "27", null, "");
        Candidate agus = new Candidate("2", "Agustin Linari", "34", null, "");

        List<Candidate> list = new ArrayList<>();
        list.add(erica);
        list.add(agus);
        return list;
    }

    @Override
    public void acceptMatch(User user, Candidate candidate) throws ServiceException {

    }
}
