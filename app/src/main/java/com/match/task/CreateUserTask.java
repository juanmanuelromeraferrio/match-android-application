package com.match.task;

import android.os.AsyncTask;

import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.listener.ResultLoadingListener;
import com.match.service.api.UserService;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class CreateUserTask extends AsyncTask<User, Void, TaskResponse> {

    private UserService userService;
    private ResultLoadingListener listener;


    public CreateUserTask(UserService userService, ResultLoadingListener listener) {
        this.userService = userService;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        if (listener != null)
            listener.show();
    }

    @Override
    protected TaskResponse doInBackground(User... params) {
        User user = params[0];
        try {
            userService.createUser(user);
        } catch (ServiceException e) {
            return new TaskResponse(e.getMessage());
        }

        return new TaskResponse(user);
    }

    @Override
    protected void onPostExecute(TaskResponse response) {
        if (listener != null)
            listener.dismiss();

        listener.notifyResult(response);
    }
}
