package com.match.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.service.api.UserService;
import com.match.service.factory.ServiceFactory;
import com.match.utils.WaitForInternet;
import com.match.utils.WaitForInternetCallback;
import com.parse.ParseUser;

/**
 * Created by Juan Manuel Romera on 18/5/2016.
 */
public class DispatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WaitForInternetCallback callback = new WaitForInternetCallback(this) {
            public void onConnectionSuccess() {
                UserService userService = ServiceFactory.getUserService();
                User localUser = userService.getLocalUser();
                try {
                    if (userService.isUserLogged(localUser)) {
                        if (userService.hasSavedInformation()) {
                            startActivity(new Intent(mActivity, HomeActivity.class));
                        } else {
                            startActivity(new Intent(mActivity, LoginActivity.class));
                        }
                    } else {
                        startActivity(new Intent(mActivity, LoginActivity.class));
                    }
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        };
        WaitForInternet.setCallback(callback);
    }
}
