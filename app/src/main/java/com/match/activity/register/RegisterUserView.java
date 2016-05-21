package com.match.activity.register;

import com.match.activity.api.BaseView;

/**
 * Created by Juan Manuel Romera on 21/5/2016.
 */
public interface RegisterUserView extends BaseView {

    void setLocationError();

    void setPhotoError();

    void onError(String errorMsg);
}