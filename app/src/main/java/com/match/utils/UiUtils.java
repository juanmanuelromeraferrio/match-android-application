package com.match.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.match.R;
import com.match.activity.login.LoginActivity;
import com.match.activity.register.RegisterUserActivity;
import com.match.service.factory.ServiceFactory;

/**
 * Created by Juan Manuel Romera on 21/5/2016.
 */
public class UiUtils {

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void showSessionExpired(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        builder.setTitle(R.string.session_expired_title);
        builder.setMessage(context.getResources().getString(R.string.session_expired_message));
        builder.setCancelable(Boolean.FALSE);
        builder.setPositiveButton(context.getResources().getString(R.string.aceptar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                ServiceFactory.getUserService().logout();
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
