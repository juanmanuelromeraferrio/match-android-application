package com.match.activity.chat;

import android.app.Activity;

import com.match.service.api.ChatService;
import com.match.service.factory.ServiceFactory;
import com.match.service.impl.ChatServiceImpl;
import com.match.task.PullHistoryTask;
import com.match.task.SendMessageTask;

/**
 * Created by pablo on 20/06/16.
 */
public class ChatControllerImpl implements ChatController{

    private ChatActivity activity;

    public ChatControllerImpl(ChatActivity activity){
        this.activity = activity;

    }

    public void pullHistory(String idFrom, String idTo){
        PullHistoryTask task = new PullHistoryTask(ServiceFactory.getChatService(),this);
        activity.disableSendButton();
        task.execute(idFrom, idTo);
    }

    @Override
    public void sendMessage(String idFrom, String idUTo,String msg) {
        SendMessageTask task = new SendMessageTask(ServiceFactory.getChatService(),this);
        activity.disableSendButton();
        task.execute(idFrom, idUTo,msg);
    }

    @Override
    public void initTask() {
        activity.clearMessage();
    }

    @Override
    public void finishTask() {

    }

    @Override
    public void onResult(Object result) {
        String msgPull = (String) result;
        activity.enableSendButton();
    }
}
