package com.match.service.impl;

import com.match.client.MatchClient;
import com.match.client.entities.Candidate;
import com.match.client.entities.User;
import com.match.client.entities.response.CandidatesResponse;
import com.match.client.entities.response.MatchResponse;
import com.match.client.entities.response.UserResponse;
import com.match.error.service.APIError;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;
import com.match.service.api.CandidatesService;
import com.match.service.api.ServiceType;
import com.match.utils.ErrorUtils;
import com.match.utils.mapper.CandidateMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Juan Manuel Romera on 29/5/2016.
 */
public class CandidatesServiceImpl extends CandidatesService {

    public CandidatesServiceImpl(Database database, CandidateMapper mapper) {
        super(database, mapper);
    }

    @Override
    public List<Candidate> findCandidates(User user) throws ServiceException {
        MatchClient matchClient = new MatchClient();
        Call<CandidatesResponse> call = matchClient.candidates.findCandidates(user.getId());
        try {
            Response<CandidatesResponse> response = call.execute();
            if (response.isSuccessful()) {
                CandidatesResponse candidatesResponse = response.body();
                return mapToCandidates(candidatesResponse);
            } else {
                APIError error = ErrorUtils.parseError(response);
                throw new ServiceException(error.getData());
            }

        } catch (IOException e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    private List<Candidate> mapToCandidates(CandidatesResponse candidatesResponse) {
        List<Candidate> candidates = new ArrayList<>();
        for (User user_ : candidatesResponse.getUsers()) {
            Candidate candidate = mapper.map(user_);
            candidates.add(candidate);
        }

        return candidates;
    }

    @Override
    public Boolean voteYes(String userId, String candidateID) throws ServiceException {
        return Boolean.FALSE;
    }

    @Override
    public void voteNo(String userId, String candidateID) throws ServiceException {

    }
}
