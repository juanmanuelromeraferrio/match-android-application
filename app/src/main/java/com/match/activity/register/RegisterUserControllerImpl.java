package com.match.activity.register;

import android.graphics.Bitmap;

import com.match.client.entities.Interest;
import com.match.client.entities.Location;
import com.match.service.api.InterestService;
import com.match.service.api.UserService;
import com.match.service.factory.ServiceFactory;
import com.match.task.response.TaskResponse;
import com.match.task.UpdateUserTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Juan Manuel Romera on 19/5/2016.
 */
public class RegisterUserControllerImpl implements RegisterUserController {

    private static final boolean LOCAL_INTERESTS = true;

    private RegisterUserView view;
    private UserService userService;
    private InterestService interestService;

    public RegisterUserControllerImpl(RegisterUserView view) {
        this.view = view;
        this.userService = ServiceFactory.getUserService();
        this.interestService = ServiceFactory.getInterestService();
    }

    @Override
    public void saveUser(Bitmap photo, String sex, Location address, List<Interest> interests) {
        this.view.clearErrors();
        boolean hasInputDataErrors = hasInputDataErrors(photo, sex, address);
        if (!hasInputDataErrors) {
            UpdateUserTask task = new UpdateUserTask(userService, this);
            task.execute(address, photo, interests, sex);
        }
    }

    private boolean hasInputDataErrors(Bitmap photo, String sex, Location address) {
        boolean errors = false;

        if (photo == null) {
            view.setPhotoError();
            errors = true;
        }

        if (sex == null || sex.isEmpty()) {
            view.setSexError();
            errors = true;
        }

        if (address == null) {
            view.setLocationError();
            errors = true;
        }

        return errors;
    }

    @Override
    public List<String> getCategories() {
        return new ArrayList<>(getInterests().keySet());
    }

    @Override
    public List<String> getInterestValues(String category) {
        return getInterests().get(category);
    }

    private Map<String, List<String>> getInterests() {
        return interestService.getLocalInterests();
    }

    @Override
    public void initTask() {
        view.showProgress();
    }

    @Override
    public void finishTask() {
        view.hideProgress();
    }

    @Override
    public void onResult(Object result) {
        TaskResponse response = (TaskResponse) result;
        if (response.hasError()) {
            view.onError(response.getError());
        } else {
            view.goToNext();
        }
    }
}
