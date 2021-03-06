package com.match.task;

import android.os.AsyncTask;

import com.match.activity.api.BaseController;
import com.match.client.entities.ChatMessage;
import com.match.error.service.ServiceException;
import com.match.service.api.ChatService;
import com.match.task.response.ChatTaskResponse;
import com.match.task.response.TaskResponse;
import com.match.utils.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by pablo on 20/06/16.
 */
public class PullNewMessagesTask extends AsyncTask<String, Void, TaskResponse> {

    private final ChatService chatService;
    private final BaseController controller;

    public PullNewMessagesTask(ChatService chatService, BaseController controller) {
        this.chatService = chatService;
        this.controller = controller;
    }

    @Override
    protected TaskResponse doInBackground(String... params) {
        ChatTaskResponse response = new ChatTaskResponse(ChatTaskState.PULL_NEW_MSG);
        String idFrom = (String) params[0];
        String idTo = (String) params[1];

        try {
            TimeUnit.SECONDS.sleep(Configuration.CHAT_TIME_REFRESH);
        } catch (InterruptedException e) {
        }

        try {
            List<ChatMessage> chatMessages = chatService.pullNewMessages(idFrom, idTo);
            response.setResponse(chatMessages);
        } catch (ServiceException e) {
            response.setError(e.getMessage());
            response.setSessionExpired(e.isSessionExpired());
            return response;
        }
        return response;
    }

    @Override
    protected void onPostExecute(TaskResponse response) {
        controller.onResult(response);
    }
}
