package com.match.fragment.profile;

import com.match.activity.api.BaseView;

/**
 * Created by Juan Manuel Romera on 21/5/2016.
 */
public interface ProfileView extends BaseView {

    void setNameError();

    void setPhotoError();
    
    void setAgeError();

    void onError(String errorMsg);

    void clearErrors();


}