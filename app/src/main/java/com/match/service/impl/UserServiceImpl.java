package com.match.service.impl;

import com.match.client.MatchClient;
import com.match.client.MatchUser;
import com.match.client.entities.Token;
import com.match.client.entities.User;
import com.match.error.service.APIError;
import com.match.error.service.ServiceException;
import com.match.listener.ProgressDialogOperationListener;
import com.match.service.api.UserService;
import com.match.utils.ErrorUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Juan Manuel Romera on 16/5/2016.
 */
public class UserServiceImpl extends UserService {


    private Token token;
    private MatchClient matchClient;
    private MatchUser matchUser;
    private ProgressDialogOperationListener listener;

    public UserServiceImpl() {
        matchClient = new MatchClient();
    }

    @Override
    public void createUser(User user) throws ServiceException {
        Call<Token> call = matchClient.users.createUser(user);
        try {
            Response<Token> response = call.execute();
            if (response.isSuccessful()) {
                token = response.body();
            } else {
                APIError error = ErrorUtils.parseError(response);
                throw new ServiceException(error.getData());
            }
        } catch (IOException e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void updateUser(User user) {

    }
}
