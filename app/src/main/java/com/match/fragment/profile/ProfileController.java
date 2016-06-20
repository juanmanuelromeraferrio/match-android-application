package com.match.fragment.profile;

import android.graphics.Bitmap;

import com.match.activity.api.BaseController;
import com.match.client.entities.Interest;
import com.match.client.entities.Location;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 19/5/2016.
 */
public interface ProfileController extends BaseController {

    void updateUser(String name, String age,Bitmap photo);
}
