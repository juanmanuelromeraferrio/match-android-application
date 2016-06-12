package com.match.service.impl;

import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.service.api.ChatService;

import java.util.List;

/**
 * Created by pablo on 10/06/16.
 */
public class ChatServiceImpl extends ChatService{

    public ChatServiceImpl() {
        super();
    }

    public List<User> getUsersMatch(String id) throws ServiceException{
        return null;
    }
}
