package com.match.task;

import android.os.AsyncTask;

import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.listener.ProgressDialogOperationListener;
import com.match.listener.ResultLoadingListener;
import com.match.service.api.UserService;
import com.match.service.factory.ServiceFactory;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class CreateUserTask extends AsyncTask<User, Void, TaskResponse> {

    private ResultLoadingListener listener;

    public CreateUserTask(ResultLoadingListener listener) {
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
        UserService userService = ServiceFactory.getInstance().getUserService();
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
