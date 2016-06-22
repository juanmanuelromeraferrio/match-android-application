package com.match.service.impl;

import android.util.Log;

import com.match.client.MatchClient;
import com.match.client.entities.ChatMessage;
import com.match.client.entities.User;
import com.match.client.entities.request.ChatPullRequest;
import com.match.client.entities.request.ChatRequest;
import com.match.client.entities.request.ChatSetLastMessageRequest;
import com.match.client.entities.response.ChatResponse;
import com.match.client.entities.response.ChatsResponse;
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
import java.util.ArrayList;
import java.util.Collection;
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

    public List<ChatMessage> pullHistory(String idFrom, String idTo) throws ServiceException{
        MatchClient matchClient = clientService.getAuthClient();
        Call<ChatsResponse> call = matchClient.chat.pullHistory(idFrom,idTo);
        List<ChatMessage> msgs = new ArrayList<>();
        try {
            Response<ChatsResponse> response = call.execute();
            if (!response.isSuccessful()) {
                Boolean sessionExpired = ErrorUtils.sessionExpired(response);
                if (sessionExpired) {
                    APIError error = ErrorUtils.parseError(response);
                    throw new ServiceException(error);
                } else {
                    Log.e(Configuration.LOG, response.errorBody().toString());
                }
            } else {
                ChatsResponse chatResponse = response.body();
                msgs.addAll(addChatToChats(chatResponse));
                //Save Token
                clientService.saveToken(response.headers());
            }
        } catch (IOException e) {
            Log.e(Configuration.LOG, e.getLocalizedMessage());
        }

        return msgs;
    }

    private Collection<? extends ChatMessage> addChatToChats(ChatsResponse chatsResponse) {

        List<ChatMessage> chats = new ArrayList<>();

        for(ChatMessage chatMessage : chatsResponse.getChat()){
           chats.add(chatMessage);
        }
        return chats;
    }

    public void pullNewMessages(String idFrom, String idTo) throws ServiceException{
        MatchClient matchClient = clientService.getAuthClient();
        Call<ChatResponse> call = matchClient.chat.pullNewMessages(idFrom,idTo);
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

    @Override
    public void setLastMessage(String idFrom, String idTo, String idMsg) throws ServiceException {
        MatchClient matchClient = clientService.getAuthClient();
        Call<ChatResponse> call = matchClient.chat.setLastMessage(new ChatSetLastMessageRequest(idFrom,idTo, idMsg));
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
