package com.match.client.utils;

import android.os.AsyncTask;

import java.io.IOException;

import retrofit2.Call;

/**
 * Created by Juan Manuel Romera on 4/5/2016.
 */
public class MatchAsyncTask<T> extends AsyncTask<Call<T>, Void, T> {

    @Override
    protected T doInBackground(Call<T>... params) {
        try {
            return params[0].execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
