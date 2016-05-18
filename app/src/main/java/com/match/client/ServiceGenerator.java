package com.match.client;

import com.match.client.entities.Token;
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
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class ServiceGenerator {

    public static Retrofit defaultRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Configuration.DEFAULT_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit authorizationRetrofit(Token token) {
        // add authentication header field
        final String authStringEncoded = encode(token.getCode().getBytes());
        final String authHeader = "Basic " + authStringEncoded;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("User-Agent", "match-android")
                        .header("Authorization", authHeader)
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.DEFAULT_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    public static <S> S createDefaultService(Class<S> serviceClass) {
        Retrofit retrofit = defaultRetrofit();
        return retrofit.create(serviceClass);
    }

    public static <S> S createAuthorizationService(Class<S> serviceClass, Token token) {
        Retrofit retrofit = authorizationRetrofit(token);
        return retrofit.create(serviceClass);
    }
}
