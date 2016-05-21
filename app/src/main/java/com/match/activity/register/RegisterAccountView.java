package com.match.activity.register;

import com.match.activity.api.BaseView;

/**
 * Created by Juan Manuel Romera on 21/5/2016.
 */
public interface RegisterAccountView extends BaseView {

    void setNameError();

    void setEmailError();

    void setPasswordError();

    void onError(String errorMsg);

}