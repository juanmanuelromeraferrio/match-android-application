package com.match.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.match.client.entities.Location;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.listener.ResultLoadingListener;
import com.match.service.api.UserService;
import com.match.service.factory.ServiceFactory;
import com.match.utils.PhotoUtils;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class UpdateUserTask extends AsyncTask<Object, Void, TaskResponse> {

    private ResultLoadingListener listener;
    private UserService userService;

    public UpdateUserTask(UserService userService, ResultLoadingListener listener) {
        this.userService = userService;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        if (listener != null)
            listener.show();
    }

    @Override
    protected TaskResponse doInBackground(Object... params) {
        Location location = (Location) params[0];
        Bitmap bitmap = (Bitmap) params[1];

        User localUser = userService.getLocalUser();
        localUser.setLocation(location);
        localUser.setPhoto(PhotoUtils.bitmapToBase64(bitmap));

        try {
            userService.updateUser(localUser);
        } catch (ServiceException e) {
            return new TaskResponse(e.getMessage());
        }

        return new TaskResponse(localUser);
    }

    @Override
    protected void onPostExecute(TaskResponse response) {
        if (listener != null)
            listener.dismiss();

        listener.notifyResult(response);
    }
}
