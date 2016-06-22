package com.match.service.impl;

import android.util.Log;

import com.match.client.MatchClient;
import com.match.client.entities.Candidate;
import com.match.client.entities.Chat;
import com.match.client.entities.User;
import com.match.client.entities.request.MatchRequest;
import com.match.client.entities.response.CandidatesResponse;
import com.match.client.entities.response.MatchResponse;
import com.match.client.entities.response.UserResponse;
import com.match.error.service.APIError;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;

import com.match.service.api.ClientService;

import com.match.service.api.UserMatchesService;
import com.match.utils.Configuration;
import com.match.utils.ErrorUtils;
import com.match.utils.mapper.CandidateMapper;
import com.match.utils.mapper.ChatMapper;

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

    public UserMatchesServiceImpl(Database database, ClientService clientService, ChatMapper mapper) {
        super(database, mapper);
        this.clientService = clientService;
    }

    @Override
    public List<Chat> findUserMatches(User user) throws ServiceException {
        MatchClient matchClient = clientService.getAuthClient();
        List<Chat> chats = null;
        Call<CandidatesResponse> call = matchClient.matches.findMatches(user.getId());
        try {
            Response<CandidatesResponse> response = call.execute();
            if (response.isSuccessful()) {
                //Get Candidates
                CandidatesResponse candidatesResponse = response.body();
                chats = mapToChats(candidatesResponse);
                //Save Token
                clientService.saveToken(response.headers());
                return chats;

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
            throw new ServiceException(e.getLocalizedMessage());
        }
        chats = new ArrayList<>();
        return chats;
    }

    @Override
    public void acceptMatch(User user, Candidate candidate) throws ServiceException {
        MatchClient matchClient = clientService.getAuthClient();
        Call<MatchResponse> call = matchClient.matches.acceptMatch(new MatchRequest(user.getId(), candidate.getId()));

        try {
            Response<MatchResponse> response = call.execute();
            if (response.isSuccessful()) {
                //Save Candidates
                user.getUserMatches().add(candidate);
                this.database.setUser(user);
                //Save Token
                clientService.saveToken(response.headers());
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
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    private List<Chat> mapToChats(CandidatesResponse candidatesResponse) {
        List<Chat> chats = new ArrayList<Chat>();
        for (UserResponse user_ : candidatesResponse.getUsers()) {
            Chat chat = mapper.map(user_.getUser());
            chats.add(chat);
        }
        return chats;
    }

}

