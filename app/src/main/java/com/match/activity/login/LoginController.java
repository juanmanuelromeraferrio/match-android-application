package com.match.activity.login;

import com.match.activity.api.BaseController;

/**
 * Created by Juan Manuel Romera on 21/5/2016.
 */
public interface LoginController extends BaseController{

    void loginUser(String email, String password);

    boolean userHasSavedInformation();
}
