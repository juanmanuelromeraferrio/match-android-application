package com.match.client;

import com.match.client.entities.Token;
import com.match.utils.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(Configuration.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        httpClient.connectTimeout(Configuration.CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        httpClient.writeTimeout(Configuration.CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS);

        return new Retrofit.Builder()
                .baseUrl(Configuration.DEFAULT_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public static Retrofit authorizationRetrofit(Token token) {
        // add authentication header field
        final String authStringEncoded = encode(token.getCode().getBytes());
        final String authHeader = "Basic " + authStringEncoded;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(Configuration.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        httpClient.connectTimeout(Configuration.CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS);
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
}
