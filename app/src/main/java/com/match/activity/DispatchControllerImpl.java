package com.match.activity;

import android.os.AsyncTask;

import com.match.activity.DispatchController;
import com.match.activity.DispatchView;
import com.match.service.factory.ServiceFactory;
import com.match.task.response.DispatchTask;
import com.match.task.response.TaskResponse;

/**
 * Created by Juan Manuel Romera on 17/6/2016.
 */
public class DispatchControllerImpl implements DispatchController {

    private DispatchView view;

    public DispatchControllerImpl(DispatchView view) {
        this.view = view;
    }

    @Override
    public void dispatch() {
        DispatchTask task = new DispatchTask(ServiceFactory.getUserService(), this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void initTask() {
    }

    @Override
    public void finishTask() {
    }

    @Override
    public void onResult(Object result) {
        TaskResponse taskResponse = (TaskResponse) result;
        Class<?>  activityClass = (Class<?>) taskResponse.getResponse();
        view.goToNext(activityClass);
    }
}
