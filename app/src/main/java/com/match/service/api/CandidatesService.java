package com.match.service.api;

import com.match.client.entities.Candidate;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;
import com.match.utils.mapper.CandidateMapper;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 22/5/2016.
 */
public abstract class CandidatesService implements MatchService {

    protected Database database;
    protected CandidateMapper mapper;

    protected CandidatesService(Database database, CandidateMapper mapper) {
        this.database = database;
        this.mapper = mapper;
    }

    public abstract List<Candidate> findCandidates(User user) throws ServiceException;

    public abstract Boolean voteYes(String userId, String candidateID) throws ServiceException;

    public abstract void voteNo(String userId, String candidateID) throws ServiceException;
}
