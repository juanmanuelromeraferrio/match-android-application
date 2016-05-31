package com.match.client.utils;

import com.google.gson.Gson;
import com.match.error.service.APIError;
import com.match.error.service.ServiceException;
import com.match.utils.Configuration;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Juan Manuel Romera on 31/5/2016.
 */
public class HttpClientMatch {

    private final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private Gson gson;
    private OkHttpClient client;

    public HttpClientMatch() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public <S> S put(String url, Object requestBody, Class<S> responseClass) throws ServiceException {
        String requestJson = gson.toJson(requestBody);
        RequestBody body = RequestBody.create(JSON, requestJson);
        Request request = new Request.Builder()
                .url(Configuration.DEFAULT_API_URL + url)
                .put(body)
                .build();

        return getResponse(responseClass, request);
    }

    public <S> S post(String url, Object requestBody, Class<S> responseClass) throws ServiceException {
        String requestJson = gson.toJson(requestBody);
        RequestBody body = RequestBody.create(JSON, requestJson);
        Request request = new Request.Builder()
                .url(Configuration.DEFAULT_API_URL + url)
                .post(body)
                .build();

        return getResponse(responseClass, request);
    }

    private <S> S getResponse(Class<S> responseClass, Request request) throws ServiceException {
        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                return gson.fromJson(jsonData, responseClass);
            } else {
                APIError apiError = gson.fromJson(jsonData, APIError.class);
                throw new ServiceException(apiError.getData());
            }
        } catch (IOException e) {
            throw new ServiceException("No hay conexion");
        }
    }
}
