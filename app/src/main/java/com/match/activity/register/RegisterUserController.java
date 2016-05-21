package com.match.activity.register;

import android.graphics.Bitmap;
import android.widget.AdapterView;

import com.match.activity.api.BaseController;
import com.match.client.entities.Interest;
import com.match.client.entities.Location;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 19/5/2016.
 */
public interface RegisterUserController extends BaseController {

    void saveUser(Bitmap photo, Location address, List<Interest> interests);

    List<String> getCategories();

    List<String> getInterestValues(String category);
}
