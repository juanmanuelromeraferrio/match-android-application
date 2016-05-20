package com.match.activity.register;

import android.widget.AdapterView;

/**
 * Created by Juan Manuel Romera on 19/5/2016.
 */
public interface RegisterUserController {

    void onLocationItemClicked(int pos);

    void onSaveUserButtonClicked();

    void onSelectedCategoryItem(AdapterView<?> adapter, int position);

    void onAddInterestButtonClicked();

    void onInterestItemClicked(int position);
}
