package com.match.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.match.MatchApplication;
import com.match.client.entities.Token;
import com.match.client.entities.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Juan Manuel Romera on 18/5/2016.
 */
public class MatchDatabase implements Database {

    private static final String DB = "DB";
    private static final String USER = "USER";
    private static final String TOKEN = "TOKEN";

    private User user;
    private Token token;
    private Map<String, List<String>> interests = null;

    public MatchDatabase() {
        user = get(USER, User.class);
        token = get(TOKEN, Token.class);
    }

    public User getUser() {
        return user;
    }

    @Override
    public Map<String, List<String>> getInterests() {
        return interests;
    }

    public Token getToken() {
        return token;
    }

    public void setUser(User user) {
        this.user = user;
        save(USER, user);
    }

    public void setToken(Token token) {
        this.token = token;
        save(TOKEN, token);
    }

    @Override
    public void setInterests(Map<String, List<String>> interests) {
        this.interests = interests;
    }

    public void save(String key, Object value) {
        SharedPreferences sharedPref = MatchApplication.getContext().getSharedPreferences(DB, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public <T extends Object> T get(String key, Class<T> classValue) {
        SharedPreferences sharedPref = MatchApplication.getContext().getSharedPreferences(DB, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString(key, "");
        return gson.fromJson(json, classValue);
    }
}
