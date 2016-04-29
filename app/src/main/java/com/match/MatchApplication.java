package com.match;

import android.app.Application;
import android.content.Context;

/**
 * Created by Juan Manuel Romera on 13/9/2015.
 */
public class MatchApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }

}

