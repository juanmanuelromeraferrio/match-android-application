package com.match.service.impl;

import com.google.gson.Gson;
import com.match.client.MatchClient;
import com.match.client.entities.Candidate;
import com.match.client.entities.User;
import com.match.client.entities.request.MatchRequest;
import com.match.client.entities.response.CandidatesResponse;
import com.match.client.entities.response.MatchResponse;
import com.match.client.entities.response.UserResponse;
import com.match.error.service.APIError;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;
import com.match.service.api.CandidatesService;
import com.match.service.api.UserMatchesService;
import com.match.utils.ErrorUtils;
import com.match.utils.mapper.CandidateMapper;

import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by pablo on 11/06/16.
 */
public class UserMatchesServiceImpl extends UserMatchesService {

    private MatchClient matchClient = new MatchClient();

    public UserMatchesServiceImpl(Database database, CandidateMapper mapper) {
        super(database, mapper);
    }

    @Override
    public List<Candidate> findUserMatches(User user) throws ServiceException {
        Call<CandidatesResponse> call = matchClient.matches.findMatches(user.getId());
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

    @Override
    public void acceptMatch(User user, Candidate candidate) throws ServiceException{

        Call<MatchResponse> call = matchClient.matches.acceptMatch(user.getId(),candidate.getId());

        try {
            Response<MatchResponse> response = call.execute();
            if (response.isSuccessful()) {
                user.getUserMatches().add(candidate);
                this.database.setUser(user);
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
        for (UserResponse user_ : candidatesResponse.getUsers()) {
            Candidate candidate = mapper.map(user_.getUser());
            candidates.add(candidate);
        }

        return candidates;
    }

}

