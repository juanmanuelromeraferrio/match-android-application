package com.match.activity.register;

import com.match.activity.api.BaseController;
import com.match.client.entities.User;
import com.match.service.api.UserService;
import com.match.service.factory.ServiceFactory;
import com.match.task.CreateUserTask;
import com.match.task.TaskResponse;
import com.match.utils.Validator;

/**
 * Created by Juan Manuel Romera on 21/5/2016.
 */
public class RegisterAccountControllerImpl implements RegisterAccountController {

    private RegisterAccountView view;
    private Validator validator;

    public RegisterAccountControllerImpl(RegisterAccountView view) {
        this.view = view;
        this.validator = new Validator(view.getContext());
    }


    @Override
    public void registerUser(String name, String email, String password) {
        boolean hasInputDataErrors = hasInputDataErrors(name, email, password);
        if (!hasInputDataErrors) {
            CreateUserTask task = new CreateUserTask(ServiceFactory.getUserService(), ServiceFactory.getInterestService(), this);
            task.execute(new User(name, email, password));
        }
    }

    private boolean hasInputDataErrors(String name, String email, String password) {
        boolean error = false;

        if (!validator.isUserNameValid(name)) {
            this.view.setNameError();
            error = true;
        }

        if (!validator.isEmailValid(email)) {
            this.view.setEmailError();
            error = true;
        }

        if (!validator.isPasswordValid(password)) {
            this.view.setPasswordError();
            error = true;
        }

        return error;
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
