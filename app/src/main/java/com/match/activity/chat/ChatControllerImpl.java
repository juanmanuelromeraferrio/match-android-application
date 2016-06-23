package com.match.activity.chat;

import android.os.AsyncTask;

import com.match.client.entities.ChatMessage;
import com.match.service.factory.ServiceFactory;
import com.match.task.PullHistoryTask;
import com.match.task.PullNewMessagesTask;
import com.match.task.SendMessageTask;
import com.match.task.SetLastMessageTask;
import com.match.task.response.ChatTaskResponse;

import java.util.List;

/**
 * Created by pablo on 20/06/16.
 */
public class ChatControllerImpl implements ChatController {

    private ChatView view;
    private String idFrom;
    private String idTo;

    public ChatControllerImpl(ChatView view, String idFrom, String idTo) {
        this.view = view;
        this.idFrom = idFrom;
        this.idTo = idTo;
    }

    @Override
    public void pullHistory() {
        PullHistoryTask task = new PullHistoryTask(ServiceFactory.getChatService(), this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, idFrom, idTo);
    }

    @Override
    public void sendMessage(String msg) {
        ChatMessage chatSent = new ChatMessage("", idFrom, msg);
        view.addMessage(chatSent);
        view.clearTextBox();

        SendMessageTask task = new SendMessageTask(ServiceFactory.getChatService(), this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, idFrom, idTo, msg);
    }

    @Override
    public void pullNewMessages() {
        if (view.isRunning()) {
            PullNewMessagesTask task = new PullNewMessagesTask(ServiceFactory.getChatService(), this);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, idFrom, idTo);
        }
    }

    @Override
    public void setLastMessage(String idFrom, String idTo, String idMsg) {
        SetLastMessageTask task = new SetLastMessageTask(ServiceFactory.getChatService(), this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, idFrom, idTo, idMsg);
    }

    @Override
    public List<ChatMessage> getMessages() {
        return ServiceFactory.getUserService().getLocalUser().getChatsMessages(idTo);
    }

    @Override
    public void initTask() {
        this.view.showProgress();
    }

    @Override
    public void finishTask() {
        this.view.hideProgress();
    }

    @Override
    public void onResult(Object result) {

        ChatTaskResponse response = (ChatTaskResponse) result;
        if (response.sessionExpired()) {
            view.sessionExpired();
        } else if (response.hasError()) {
            this.view.onError(response.getError());
        } else {
            switch (response.getState()) {
                case PULL_NEW_MSG: {
                    List<ChatMessage> messageList = (List<ChatMessage>) response.getResponse();
                    view.addMessages(Boolean.TRUE, messageList);
                    break;
                }
                case PULL_HISTORY: {
                    List<ChatMessage> messageList = (List<ChatMessage>) response.getResponse();
                    view.addMessages(Boolean.FALSE, messageList);
                    break;
                }
                case SET_LAST: {
                    pullNewMessages();
                    break;
                }
            }
        }
    }
}
