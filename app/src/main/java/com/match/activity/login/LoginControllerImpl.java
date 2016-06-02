package com.match.activity.login;

import com.match.R;
import com.match.service.factory.ServiceFactory;
import com.match.task.LoginUserTask;
import com.match.task.response.LoginTaskResponse;
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
            this.view.setEmailError(view.getContext().getResources().getString(R.string.error_mail));
            error = true;
        }

        if (!validator.isPasswordValid(password)) {
            this.view.setPasswordError(view.getContext().getResources().getString(R.string.error_password, Validator.MIN_LENGTH_PASSWORD, Validator.MAX_LENGTH_PASSWORD));
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
        LoginTaskResponse response = (LoginTaskResponse) result;
        if (response.hasError()) {
            view.onError(response.getError());
        } else if (response.hasUserError()) {
            view.setEmailError(response.getUserError());
        } else if (response.hasPasswordError()) {
            view.setPasswordError(response.getPasswordError());
        } else {
            view.goToNext();
        }
    }
}
