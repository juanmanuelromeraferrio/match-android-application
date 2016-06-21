package com.match.service.impl;

import android.util.Log;

import com.match.client.MatchClient;
import com.match.client.entities.User;
import com.match.client.entities.request.ChatRequest;
import com.match.client.entities.response.ChatResponse;
import com.match.error.service.APIError;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;
import com.match.service.api.ChatService;
import com.match.service.api.ClientService;
import com.match.task.response.ChatTaskResponse;
import com.match.utils.Configuration;
import com.match.utils.ErrorUtils;
import com.match.utils.mapper.CandidateMapper;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by pablo on 10/06/16.
 */
public class ChatServiceImpl extends ChatService{

    private ClientService clientService;

    public ChatServiceImpl(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void sendMessage(String idUserLocal, String idUserMatch, String msg) throws ServiceException {
        MatchClient matchClient = clientService.getAuthClient();
        Call<ChatResponse> call = matchClient.chat.sendMessage(new ChatRequest(idUserLocal,idUserMatch,msg));
        try {
            Response<ChatResponse> response = call.execute();
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
}
