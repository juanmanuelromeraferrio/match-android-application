package com.match.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.match.activity.api.BaseController;
import com.match.client.entities.Interest;
import com.match.client.entities.Location;
import com.match.client.entities.User;
import com.match.service.api.UserService;
import com.match.task.response.TaskResponse;
import com.match.utils.PhotoUtils;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class UpdateUserTask extends AsyncTask<Object, Void, TaskResponse> {

    private UserService userService;
    private BaseController controller;

    public UpdateUserTask(UserService userService, BaseController controller) {
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
        Location location = (Location) params[0];
        Bitmap bitmap = (Bitmap) params[1];
        List<Interest> interests = (List<Interest>) params[2];
        String sex = (String) params[3];

        try {

            User localUser = userService.getLocalUser();
            localUser.setLocation(location);
            localUser.setPhoto(PhotoUtils.bitmapToBase64(bitmap));
            localUser.setInterests(interests);
            localUser.setSex(sex);
            userService.updateUser(localUser);
        } catch (Exception e) {
            return new TaskResponse(e.getMessage());
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
