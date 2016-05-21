package com.match.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.match.activity.login.LoginActivity;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.service.api.UserService;
import com.match.service.factory.ServiceFactory;

/**
 * Created by Juan Manuel Romera on 18/5/2016.
 */
public class DispatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserService userService = ServiceFactory.getUserService();
        User localUser = userService.getLocalUser();
        try {
            if (userService.isUserLogged(localUser)) {
                if (userService.hasSavedInformation()) {
                    startActivity(new Intent(this, HomeActivity.class));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
