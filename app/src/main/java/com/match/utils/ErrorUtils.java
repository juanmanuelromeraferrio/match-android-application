package com.match.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.match.R;
import com.match.client.ServiceGenerator;
import com.match.client.entities.response.MatchResponse;
import com.match.error.service.APIError;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.http.HTTP;

/**
 * Created by Juan Manuel Romera on 7/10/2015.
 */
public class ErrorUtils {

    public static AlertDialog createNoConnectionDialog(final String title, final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setIcon(R.drawable.ic_no_connection);
        builder.setMessage(context.getResources().getString(R.string.ERROR_NO_CONECTIVIDAD_MENSAJE))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        return alert;
    }

    public static APIError parseError(Response<?> response) {
        APIError error;
        if (sessionExpired(response)) {
            error = new APIError();
            error.setSessionExpired(Boolean.TRUE);
        } else {
            Converter<ResponseBody, APIError> converter =
                    ServiceGenerator.defaultRetrofit()
                            .responseBodyConverter(APIError.class, new Annotation[0]);
            try {
                error = converter.convert(response.errorBody());
            } catch (IOException e) {
                return new APIError(response.raw().toString());
            }
        }


        return error;
    }

    public static Boolean sessionExpired(Response<?> response) {
        return response.code() == Configuration.HTTP_CODE_FORBIDDEN;
    }
}
