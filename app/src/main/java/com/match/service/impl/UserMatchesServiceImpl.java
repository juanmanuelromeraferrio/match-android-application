package com.match.service.impl;

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
import com.match.service.api.UserMatchesService;
import com.match.utils.ErrorUtils;
import com.match.utils.mapper.CandidateMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by pablo on 11/06/16.
 */
public class UserMatchesServiceImpl extends UserMatchesService {

    public UserMatchesServiceImpl(Database database, CandidateMapper mapper) {
        super(database, mapper);
    }

    public ArrayList<Candidate> findUserMatches(User user) throws ServiceException {
        MatchClient matchClient = new MatchClient();
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
        MatchClient matchClient = new MatchClient();

        Call<MatchResponse> call = matchClient.matches.acceptMatch(new MatchRequest(user.getId(),candidate.getId()));

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

    private ArrayList<Candidate> mapToCandidates(CandidatesResponse candidatesResponse) {
        ArrayList<Candidate> candidates = new ArrayList<Candidate>();
        for (UserResponse user_ : candidatesResponse.getUsers()) {
            Candidate candidate = mapper.map(user_.getUser());
            candidates.add(candidate);
        }

        return candidates;
    }

}

