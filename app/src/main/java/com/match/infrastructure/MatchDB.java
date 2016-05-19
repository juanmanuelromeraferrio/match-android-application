package com.match.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.match.MatchApplication;
import com.match.client.entities.Token;
import com.match.client.entities.User;
import com.match.utils.Configuration;

/**
 * Created by Juan Manuel Romera on 18/5/2016.
 */
public class MatchDB {

    private static final String DB = "DB";
    private static final String USER = "USER";
    private static final String TOKEN = "TOKEN";
    private static final String TIME = "TIME";

    private User user;
    private Token token;

    private long lastTimePersist = 0;

    public MatchDB() {

        lastTimePersist = get(TIME);
        user = get(USER, User.class);
        token = get(TOKEN, Token.class);
    }

    public User getUser() {
        if (useCache()) {
            return user;
        } else {
            return get(USER, User.class);
        }
    }

    public Token getToken() {
        if (useCache()) {
            return token;
        } else {
            return get(TOKEN, Token.class);
        }
    }

    public void setUser(User user) {
        this.user = user;
        this.lastTimePersist = System.currentTimeMillis();
        save(USER, user);
    }

    public void setToken(Token token) {
        this.token = token;
        this.lastTimePersist = System.currentTimeMillis();
        save(TOKEN, token);
    }

    private boolean useCache() {

        if (lastTimePersist == 0) {
            return true;
        }

        long actual = System.currentTimeMillis();
        long diff = actual - lastTimePersist;
        if (diff < Configuration.TOKEN_TIME_MS) {
            return true;
        }

        return false;
    }

    public void save(String key, Object value) {
        SharedPreferences sharedPref = MatchApplication.getContext().getSharedPreferences(DB, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        prefsEditor.putString(key, json);
        prefsEditor.putLong(TIME, lastTimePersist);
        prefsEditor.commit();
    }

    public long get(String key) {
        SharedPreferences sharedPref = MatchApplication.getContext().getSharedPreferences(DB, Context.MODE_PRIVATE);
        return sharedPref.getLong(key, 0);
    }

    public <T extends Object> T get(String key, Class<T> classValue) {
        SharedPreferences sharedPref = MatchApplication.getContext().getSharedPreferences(DB, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString(key, "");
        return gson.fromJson(json, classValue);
    }
}
