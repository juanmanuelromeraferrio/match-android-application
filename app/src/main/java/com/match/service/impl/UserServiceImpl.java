package com.match.service.impl;

import com.match.client.MatchClient;
import com.match.client.entities.User;
import com.match.client.entities.request.UserRequest;
import com.match.client.entities.response.MatchResponse;
import com.match.client.entities.response.UserResponse;
import com.match.client.utils.HttpClientMatch;
import com.match.error.service.APIError;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;
import com.match.service.api.UserService;
import com.match.utils.ErrorUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Juan Manuel Romera on 16/5/2016.
 */
public class UserServiceImpl extends UserService {

    public UserServiceImpl(Database database) {
        super(database);
    }

    @Override
    public void createUser(User user) throws ServiceException {
        MatchClient matchClient = new MatchClient();
        Call<MatchResponse> call = matchClient.users.createUser(new UserRequest(user));
        try {
            Response<MatchResponse> response = call.execute();
            if (response.isSuccessful()) {
                MatchResponse matchResponse = response.body();
                user.setId(matchResponse.getData());
                this.database.setUser(user);
            } else {
                APIError error = ErrorUtils.parseError(response);
                throw new ServiceException(error.getData());
            }
        } catch (IOException e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        MatchClient matchClient = new MatchClient();
        Call<MatchResponse> call = matchClient.users.updateUser(new UserRequest(user));
        try {
            Response<MatchResponse> response = call.execute();
            if (response.isSuccessful()) {
                this.database.setUser(user);
            } else {
                APIError error = ErrorUtils.parseError(response);
                throw new ServiceException(error.getData());
            }

        } catch (IOException e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public boolean isUserLogged(User user) throws ServiceException {
        return false;
    }

    @Override
    public void loginUser(String email, String password) throws ServiceException {

    }
}
