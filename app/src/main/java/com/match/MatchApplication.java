package com.match;

import android.app.Application;
import android.content.Context;

import com.match.client.MatchClient;
import com.match.infrastructure.Database;
import com.match.infrastructure.MatchDatabase;
import com.match.service.api.Services;
import com.match.service.factory.ServiceFactory;

/**
 * Created by Juan Manuel Romera on 13/9/2015.
 */
public class MatchApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initConfiguration();
    }

    private void initConfiguration() {
        Database database = new MatchDatabase();
        ServiceFactory.init(Services.REAL, database);
    }

    public static Context getContext() {
        return mContext;
    }

}

