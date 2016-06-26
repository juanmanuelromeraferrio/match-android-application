package com.match.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.match.activity.api.BaseController;
import com.match.error.service.ServiceException;
import com.match.service.api.CandidatesService;
import com.match.task.response.CandidateTaskResponse;
import com.match.task.response.PhotoTaskResponse;
import com.match.task.response.TaskResponse;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class GetPhotoTask extends AsyncTask<Object, Void, TaskResponse> {

    private CandidatesService candidatesService;
    private BaseController controller;

    public GetPhotoTask(CandidatesService candidatesService, BaseController controller) {
        this.candidatesService = candidatesService;
        this.controller = controller;
    }

    @Override
    protected TaskResponse doInBackground(Object... params) {
        CandidateTaskResponse response = new CandidateTaskResponse(CandidateTaskState.GET_PHOTO);
        String candidateID = (String) params[0];
        String photo = null;
        try {
            photo = candidatesService.findPhoto(candidateID);
        } catch (ServiceException e) {
            response.setError(e.getMessage());
            response.setSessionExpired(e.isSessionExpired());
            return response;
        }

        response.setResponse(new PhotoTaskResponse(candidateID, photo));
        return response;
    }

    @Override
    protected void onPostExecute(TaskResponse response) {
        controller.onResult(response);
    }
}
