package com.match.activity.chat;

import android.os.AsyncTask;

import com.match.client.entities.Candidate;
import com.match.client.entities.Chat;
import com.match.service.api.UserMatchesService;
import com.match.service.api.UserService;
import com.match.service.factory.ServiceFactory;
import com.match.task.FindChatsTask;
import com.match.task.response.TaskResponse;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 21/6/2016.
 */
public class ChatControllerLobbyImpl implements ChatControllerLobby {

    private ChatLobbyView view;
    private UserService userService;
    private UserMatchesService userMatchesService;

    public ChatControllerLobbyImpl(ChatLobbyView view) {
        this.view = view;
        this.userService = ServiceFactory.getUserService();
        this.userMatchesService = ServiceFactory.getUserMatchesService();
    }

    @Override
    public void findChats() {
        FindChatsTask task = new FindChatsTask(userMatchesService, this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, userService.getLocalUser());
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
        TaskResponse response = (TaskResponse) result;
        if (response.sessionExpired()) {
            view.sessionExpired();
        } else if (response.hasError()) {
            this.view.onError(response.getError());
        } else {
            List<Chat> chats = (List<Chat>) response.getResponse();
            this.view.addChats(chats);
        }

    }
}
