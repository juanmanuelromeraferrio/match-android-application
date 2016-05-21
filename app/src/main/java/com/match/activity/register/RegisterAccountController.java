package com.match.activity.register;

import com.match.activity.api.BaseController;

/**
 * Created by Juan Manuel Romera on 21/5/2016.
 */
public interface RegisterAccountController extends BaseController {

    void registerUser(String name, String email, String password);
}
