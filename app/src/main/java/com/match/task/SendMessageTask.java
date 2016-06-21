package com.match.task;

import android.os.AsyncTask;

import com.match.activity.api.BaseController;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.service.api.ChatService;
import com.match.task.response.ChatTaskResponse;
import com.match.task.response.TaskResponse;

/**
 * Created by pablo on 20/06/16.
 */
public class SendMessageTask extends AsyncTask<String,Void, TaskResponse> {

    private final ChatService chatService;
    private final BaseController controller;

    public SendMessageTask(ChatService chatService, BaseController controller){
        this.chatService = chatService;
        this.controller = controller;
    }

    @Override
    protected TaskResponse doInBackground(String... params) {
        ChatTaskResponse response = new ChatTaskResponse(ChatTaskState.SEND_MSG);
        String idFrom = (String) params[0];
        String idTo = (String) params[1];
        String msg = (String) params[2];

        try {
            chatService.sendMessage(idFrom,idTo,msg);
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
