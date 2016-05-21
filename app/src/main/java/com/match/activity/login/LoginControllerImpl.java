package com.match.activity.login;

import com.match.activity.register.RegisterAccountView;
import com.match.client.entities.User;
import com.match.service.factory.ServiceFactory;
import com.match.task.CreateUserTask;
import com.match.task.LoginUserTask;
import com.match.task.TaskResponse;
import com.match.utils.Validator;

/**
 * Created by Juan Manuel Romera on 21/5/2016.
 */
public class LoginControllerImpl implements LoginController {

    private LoginView view;
    private Validator validator;

    public LoginControllerImpl(LoginView view) {
        this.view = view;
        this.validator = new Validator(view.getContext());
    }

    @Override
    public void loginUser(String email, String password) {
        boolean hasInputDataErrors = hasInputDataErrors(email, password);
        if (!hasInputDataErrors) {
            LoginUserTask task = new LoginUserTask(ServiceFactory.getUserService(), ServiceFactory.getInterestService(), this);
            task.execute(email, password);
        }
    }

    @Override
    public boolean userHasSavedInformation() {
        return ServiceFactory.getUserService().hasSavedInformation();
    }

    private boolean hasInputDataErrors(String email, String password) {
        boolean error = false;

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
        this.view.showProgress();
    }

    @Override
    public void finishTask() {
        this.view.hideProgress();
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
