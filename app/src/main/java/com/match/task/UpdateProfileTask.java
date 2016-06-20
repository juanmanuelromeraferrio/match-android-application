package com.match.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.match.activity.api.BaseController;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.service.api.UserService;
import com.match.task.response.TaskResponse;
import com.match.utils.PhotoUtils;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class UpdateProfileTask extends AsyncTask<Object, Void, TaskResponse> {

    private UserService userService;
    private BaseController controller;

    public UpdateProfileTask(UserService userService, BaseController controller) {
        this.userService = userService;
        this.controller = controller;
    }

    @Override
    protected void onPreExecute() {
        if (controller != null)
            controller.initTask();
    }

    @Override
    protected TaskResponse doInBackground(Object... params) {
        User localUser = userService.getLocalUser();

        String name = (String) params[0];
        String age = (String) params[1];
        Bitmap bitmap = (Bitmap) params[2];

        String oldName = localUser.getName();
        String oldAge = localUser.getAge();
        String oldPhoto = localUser.getPhoto();

        try {

            localUser.setName(name);
            localUser.setAge(age);
            localUser.setPhoto(PhotoUtils.bitmapToBase64(bitmap));
            userService.updateUser(localUser);
        } catch (ServiceException e) {
            localUser.setName(oldName);
            localUser.setAge(oldAge);
            localUser.setPhoto(oldPhoto);
            TaskResponse response = new TaskResponse(e.getMessage());
            response.setSessionExpired(e.isSessionExpired());
            return response;
        }

        return new TaskResponse();
    }

    @Override
    protected void onPostExecute(TaskResponse response) {
        if (controller != null)
            controller.finishTask();

        controller.onResult(response);
    }
}
