package com.match.service.impl;

import com.match.client.entities.ChatMessage;
import com.match.error.service.ServiceException;
import com.match.service.api.ChatService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Juan Manuel Romera on 21/6/2016.
 */
public class ChatServiceMock extends ChatService {

    @Override
    public void sendMessage(String idUserLocal, String idUserMatch, String msg) throws ServiceException {
/*        new ChatMessage("", idUserLocal, msg);
        historyMessages.add(new ChatMessage("", idUserLocal, msg));
        newMessage.add(new ChatMessage("", idUserLocal, msg));*/
    }

    public List<ChatMessage> pullHistory(String idFrom, String idTo) throws ServiceException {
        List<ChatMessage> historyMessages = new ArrayList<>();
        if (historyMessages.isEmpty()) {
            ChatMessage chat1 = new ChatMessage("0", idFrom, "Hola, estas?");
            ChatMessage chat2 = new ChatMessage("0", idTo, "Si, aca estoy!");
            ChatMessage chat3 = new ChatMessage("0", idTo, "Que haces?");
            historyMessages.add(chat1);
            historyMessages.add(chat2);
            historyMessages.add(chat3);
        }

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return historyMessages;
    }

    public List<ChatMessage> pullNewMessages(String idFrom, String idTo) throws ServiceException {
        List<ChatMessage> newMessage = new ArrayList<>();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        newMessage.add(new ChatMessage("", idTo, "Este mensaje es nuevo"));
        return newMessage;
    }

    public void setLastMessage(String idFrom, String idTo, String idMsg) throws ServiceException {
        //newMessage.clear();
    }

}
