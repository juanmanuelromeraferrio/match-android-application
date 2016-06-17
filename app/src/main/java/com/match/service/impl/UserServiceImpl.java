package com.match.service.impl;

import com.match.client.MatchClient;
import com.match.client.entities.User;
import com.match.client.entities.request.LoginRequest;
import com.match.client.entities.request.UserRequest;
import com.match.client.entities.response.MatchResponse;
import com.match.client.entities.response.UserResponse;
import com.match.error.service.APIError;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;
import com.match.service.api.ClientService;
import com.match.service.api.UserService;
import com.match.utils.ErrorUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Juan Manuel Romera on 16/5/2016.
 */
public class UserServiceImpl extends UserService {

    private ClientService clientService;

    public UserServiceImpl(Database database, ClientService clientService) {
        super(database);
        this.clientService = clientService;
    }

    @Override
    public void createUser(User user) throws ServiceException {
        MatchClient matchClient = clientService.getClient();
        Call<MatchResponse> call = matchClient.users.createUser(new UserRequest(user));
        try {
            Response<MatchResponse> response = call.execute();
            if (response.isSuccessful()) {
                //Save User
                MatchResponse matchResponse = response.body();
                user.setId(matchResponse.getData());
                saveUser(user);
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

    @Override
    public void updateUser(User user) throws ServiceException {
        MatchClient matchClient = clientService.getAuthClient();
        Call<MatchResponse> call = matchClient.users.updateUser(new UserRequest(user));
        try {
            Response<MatchResponse> response = call.execute();
            if (response.isSuccessful()) {
                //Save User
                saveUser(user);
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

    @Override
    public boolean isUserLogged(User user) throws ServiceException {
        return false;
    }

    @Override
    public void loginUser(String email, String password) throws ServiceException {
        MatchClient matchClient = clientService.getClient();
        Call<UserResponse> call = matchClient.users.loginUser(new LoginRequest(email, password));
        try {
            Response<UserResponse> response = call.execute();
            if (response.isSuccessful()) {
                //Save User
                UserResponse userResponse = response.body();
                saveUser(userResponse.getUser());
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

    public void saveUser(User user) {
        this.database.setUser(user);
    }
}
