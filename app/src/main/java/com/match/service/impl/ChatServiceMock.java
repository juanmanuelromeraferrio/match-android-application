package com.match.service.impl;

import com.match.error.service.ServiceException;
import com.match.service.api.ChatService;

/**
 * Created by Juan Manuel Romera on 21/6/2016.
 */
public class ChatServiceMock extends ChatService {

    @Override
    public void sendMessage(String idUserLocal, String idUserMatch, String msg) throws ServiceException {

    }

    public void pullHistory(String idFrom, String idTo) throws ServiceException {

    }

    public void pullNewMessages(String idFrom, String idTo) throws ServiceException {

    }

    public void setLastMessage(String idFrom, String idTo, String idMsg) throws ServiceException {
        
    }

}
