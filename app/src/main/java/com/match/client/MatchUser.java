package com.match.client;

import com.match.client.entities.Token;
import com.match.client.resources.Users;
import com.match.utils.Configuration;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sun.org.apache.xml.internal.security.utils.Base64.encode;

/**
 * Created by Juan Manuel Romera on 4/5/2016.
 * This class works as a proxy to connect API rest
 * service when user is logged
 */
public class MatchUser {

    public final Users users;

    public MatchUser(Token token) {
        this.users = ServiceGenerator.createAuthorizationService(Users.class,token);
    }
}
