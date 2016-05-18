package com.match.client.utils;

import android.os.AsyncTask;

import com.match.listener.ProgressDialogOperationListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;

/**
 * Created by Juan Manuel Romera on 4/5/2016.
 */
public class MatchAsyncProgressTask<T> extends AsyncTask<Call<T>, Void, T> {

    private ProgressDialogOperationListener listener;

    public MatchAsyncProgressTask(ProgressDialogOperationListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        if (listener != null)
            listener.show();
    }

    @Override
    protected void onPostExecute(T result) {
        if (listener != null)
            listener.dismiss();
    }

    @Override
    protected T doInBackground(Call<T>... params) {
        try {
            TimeUnit.SECONDS.sleep(5);
            return params[0].execute().body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
