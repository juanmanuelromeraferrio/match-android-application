package com.match.service.impl;

import com.match.client.entities.Candidate;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;
import com.match.service.api.CandidatesService;
import com.match.service.api.ServiceType;
import com.match.utils.mapper.CandidateMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Manuel Romera on 29/5/2016.
 */
public class CandidatesServiceImpl extends CandidatesService {

    public CandidatesServiceImpl(Database database, CandidateMapper mapper) {
        super(database, mapper);
    }

    @Override
    public List<Candidate> findCandidates(User user) throws ServiceException {
        return new ArrayList<>();
    }

    @Override
    public Boolean voteYes(String userId, String candidateID) throws ServiceException {
        return Boolean.FALSE;
    }

    @Override
    public void voteNo(String userId, String candidateID) throws ServiceException {

    }
}
