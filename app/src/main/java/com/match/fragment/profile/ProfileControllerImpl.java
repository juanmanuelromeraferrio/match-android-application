package com.match.fragment.profile;

import android.graphics.Bitmap;

import com.match.service.api.UserService;
import com.match.service.factory.ServiceFactory;
import com.match.task.UpdateProfileTask;
import com.match.task.response.TaskResponse;
import com.match.utils.Validator;

/**
 * Created by Juan Manuel Romera on 19/5/2016.
 */
public class ProfileControllerImpl implements ProfileController {

    private static final boolean LOCAL_INTERESTS = true;

    private ProfileView view;
    private UserService userService;
    private Validator validator;

    public ProfileControllerImpl(ProfileView view) {
        this.view = view;
        this.userService = ServiceFactory.getUserService();
        this.validator = new Validator(view.getContext());
    }

    @Override
    public void updateUser(String name, String age, Bitmap photo) {
        this.view.clearErrors();
        boolean hasInputDataErrors = hasInputDataErrors(photo, name, age);
        if (!hasInputDataErrors) {
            UpdateProfileTask task = new UpdateProfileTask(userService, this);
            task.execute(name, age, photo);
        }
    }

    private boolean hasInputDataErrors(Bitmap photo, String name, String age) {
        boolean errors = false;

        if (photo == null) {
            view.setPhotoError();
            errors = true;
        }

        if (name == null || name.isEmpty()) {
            view.setNameError();
            errors = true;
        }

        if (!validator.isAgeValid(age)) {
            view.setAgeError();
            errors = true;
        }

        return errors;
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
        if (response.sessionExpired()) {
            view.sessionExpired();
        } else if (response.hasError()) {
            view.onError(response.getError());
        } else {
            view.goToNext();
        }
    }
}
