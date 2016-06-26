package com.match.service.impl;

import android.graphics.Bitmap;
import android.util.Log;
import com.match.client.MatchClient;
import com.match.client.entities.Candidate;
import com.match.client.entities.User;
import com.match.client.entities.request.VoteRequest;
import com.match.client.entities.response.CandidatesResponse;
import com.match.client.entities.response.MatchResponse;
import com.match.client.entities.response.PhotoResponse;
import com.match.client.entities.response.UserResponse;
import com.match.error.service.APIError;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;
import com.match.service.api.CandidatesService;
import com.match.service.api.ClientService;
import com.match.utils.Configuration;
import com.match.utils.ErrorUtils;
import com.match.utils.PhotoUtils;
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

    private ClientService clientService;

    public CandidatesServiceImpl(Database database, ClientService clientService, CandidateMapper mapper) {
        super(database, mapper);
        this.clientService = clientService;
    }

    @Override
    public List<Candidate> findCandidates(User user) throws ServiceException {

        MatchClient matchClient = clientService.getAuthClient();

        Call<CandidatesResponse> call = matchClient.candidates.findCandidates(user.getId());
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

    private List<Candidate> mapToCandidates(CandidatesResponse candidatesResponse) {
        List<Candidate> candidates = new ArrayList<>();
        for (UserResponse user_ : candidatesResponse.getUsers()) {
            Candidate candidate = mapper.map(user_.getUser());
            candidates.add(candidate);
        }

        return candidates;
    }

    @Override
    public Boolean voteYes(String userId, String candidateID) throws ServiceException {

        MatchClient matchClient = clientService.getAuthClient();

        Call<MatchResponse> call = matchClient.candidates.voteYes(new VoteRequest(userId, candidateID));
        try {
            Response<MatchResponse> response = call.execute();
            if (response.isSuccessful()) {
                //Match Response
                MatchResponse matchResponse = response.body();
                Boolean isMatch = isMatch(matchResponse.getData());
                //Save Token
                clientService.saveToken(response.headers());
                return isMatch;
            } else {
                Boolean sessionExpired = ErrorUtils.sessionExpired(response);
                if (sessionExpired) {
                    APIError error = ErrorUtils.parseError(response);
                    throw new ServiceException(error);
                } else {
                    Log.e(Configuration.LOG, response.errorBody().toString());
                }
            }
        } catch (IOException e) {
            Log.e(Configuration.LOG, e.getLocalizedMessage());
        }

        return Boolean.FALSE;
    }

    @Override
    public void voteNo(String userId, String candidateID) throws ServiceException {
        MatchClient matchClient = clientService.getAuthClient();
        Call<MatchResponse> call = matchClient.candidates.voteNo(new VoteRequest(userId, candidateID));
        try {
            Response<MatchResponse> response = call.execute();
            if (!response.isSuccessful()) {
                Boolean sessionExpired = ErrorUtils.sessionExpired(response);
                if (sessionExpired) {
                    APIError error = ErrorUtils.parseError(response);
                    throw new ServiceException(error);
                } else {
                    Log.e(Configuration.LOG, response.errorBody().toString());
                }
            } else {
                //Save Token
                clientService.saveToken(response.headers());
            }
        } catch (IOException e) {
            Log.e(Configuration.LOG, e.getLocalizedMessage());
        }
    }

    @Override
    public String findPhoto(String id) throws ServiceException {
        MatchClient matchClient = clientService.getAuthClient();
        Call<PhotoResponse> call = matchClient.users.getPhoto(id);

        try {
            Response<PhotoResponse> response = call.execute();
            if (response.isSuccessful()) {
                // Get Photo
                PhotoResponse photoResponse = response.body();
                //Save Token
                clientService.saveToken(response.headers());
                return photoResponse.getPhoto();
            } else {
                Boolean sessionExpired = ErrorUtils.sessionExpired(response);
                if (sessionExpired) {
                    APIError error = ErrorUtils.parseError(response);
                    throw new ServiceException(error);
                } else {
                    Log.e(Configuration.LOG, response.errorBody().toString());
                }
            }
        } catch (IOException e) {
            Log.e(Configuration.LOG, e.getLocalizedMessage());
        }

        return null;
    }

    private Boolean isMatch(String data) {
        return data.equals(Configuration.MATCHED_RESPONSE);
    }
}
