package com.match.service.api;

import com.match.client.entities.User;
import com.match.error.service.ServiceException;

import java.util.List;

/**
 * Created by pablo on 10/06/16.
 */
public abstract class ChatService implements MatchService {

    public abstract void sendMessage(String idUserLocal, String idUserMatch, String msg) throws ServiceException;

    public ServiceType getType() {
        return ServiceType.CHAT;
    }

    public abstract void pullHistory(String idFrom, String idTo) throws ServiceException;

}
