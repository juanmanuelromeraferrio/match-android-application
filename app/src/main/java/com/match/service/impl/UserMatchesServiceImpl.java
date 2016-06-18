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
<<<<<<< HEAD
=======
import com.match.service.api.ClientService;
>>>>>>> a7b31bd445dfa0883f1ec18b7d2c7f0087fcd181
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

    private ClientService clientService;

    public UserMatchesServiceImpl(Database database, ClientService clientService, CandidateMapper mapper) {
        super(database, mapper);
        this.clientService = clientService;
    }

<<<<<<< HEAD
    public ArrayList<Candidate> findUserMatches(User user) throws ServiceException {
        MatchClient matchClient = new MatchClient();
=======
    @Override
    public List<Candidate> findUserMatches(User user) throws ServiceException {
        MatchClient matchClient = clientService.getAuthClient();
>>>>>>> a7b31bd445dfa0883f1ec18b7d2c7f0087fcd181
        Call<CandidatesResponse> call = matchClient.matches.findMatches(user.getId());
        try {
            Response<CandidatesResponse> response = call.execute();
            if (response.isSuccessful()) {
                //Get Candidates
                CandidatesResponse candidatesResponse = response.body();
                List<Candidate> candidates = mapToCandidates(candidatesResponse);
                //Save Token
                clientService.saveToken(response.headers());
                return candidates;

            } else {
                APIError error = ErrorUtils.parseError(response);
                throw new ServiceException(error);
            }

        } catch (IOException e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
<<<<<<< HEAD
    public void acceptMatch(User user, Candidate candidate) throws ServiceException{
        MatchClient matchClient = new MatchClient();

        Call<MatchResponse> call = matchClient.matches.acceptMatch(new MatchRequest(user.getId(),candidate.getId()));
=======
    public void acceptMatch(User user, Candidate candidate) throws ServiceException {
        MatchClient matchClient = clientService.getAuthClient();
        Call<MatchResponse> call = matchClient.matches.acceptMatch(user.getId(), candidate.getId());
>>>>>>> a7b31bd445dfa0883f1ec18b7d2c7f0087fcd181

        try {
            Response<MatchResponse> response = call.execute();
            if (response.isSuccessful()) {
                //Save Candidates
                user.getUserMatches().add(candidate);
                this.database.setUser(user);
                //Save Token
                clientService.saveToken(response.headers());
            } else {
                APIError error = ErrorUtils.parseError(response);
                throw new ServiceException(error);
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

