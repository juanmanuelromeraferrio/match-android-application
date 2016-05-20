package com.match.activity.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.match.R;
import com.match.activity.HomeActivity;
import com.match.client.entities.Location;
import com.match.infrastructure.PlaceAutoCompleteAdapter;
import com.match.listener.ResultLoadingListener;
import com.match.task.TaskResponse;
import com.match.utils.PhotoUtils;
import com.match.utils.WaitForInternet;
import com.match.utils.WaitForInternetCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, GoogleApiClient.OnConnectionFailedListener, ResultLoadingListener {

    private static final int PICK_PHOTO_FOR_PROFILE = 100;

    @InjectView(R.id.input_address)
    AutoCompleteTextView _addressAutoCompleteText;
    @InjectView(R.id.btn_save)
    Button _saveButton;
    @InjectView(R.id.photoButton)
    ImageButton _addPhoto;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.spCategory)
    Spinner spCategory;
    @InjectView(R.id.spValue)
    Spinner spValue;
    @InjectView(R.id.interestList)
    ListView interestListView;

    private ArrayAdapter<String> adapterCategoryType;
    private ArrayAdapter<String> adapterValueType;
    private ArrayAdapter<String> adapterListInterest;
    private PlaceAutoCompleteAdapter locationAdapter;

    private ProgressDialog progressDialog;
    private RegisterUserControllerImpl controller;

    protected GoogleApiClient mGoogleApiClient;

    private Bitmap photo;
    private Location location;
    private Map<String, List<String>> interests = new HashMap<>();

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new RegisterUserControllerImpl(this);

        WaitForInternetCallback callback = new WaitForInternetCallback(this) {
            public void onConnectionSuccess() {
                setContentView(R.layout.activity_register_user);
                ButterKnife.inject(mActivity);
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_sign_up_user_info));
                createLocationService(mActivity);
                createInterestData(mActivity);
            }
        };
        WaitForInternet.setCallback(callback);


    }

    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_PROFILE);
    }

    public void addInterest(View view) {
        controller.onAddInterestButtonClicked();
    }

    public void saveUser(View view) {
        controller.onSaveUserButtonClicked();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_PROFILE && resultCode == Activity.RESULT_OK) {
            photo = PhotoUtils.decodeMediaImage(this, data);
            showPhoto();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterUserActivity.this, RegistarAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        controller.onSelectedCategoryItem(parent, position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,
                R.string.error_connection_google_api + " " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void show() {
        progressDialog = ProgressDialog.show(RegisterUserActivity.this, "", getResources().getString(R.string.saving_user_info), true, false);
        progressDialog.show();
    }

    @Override
    public void dismiss() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void notifyResult(Object... params) {
        TaskResponse taskResponse = (TaskResponse) params[0];
        if (taskResponse.hasError()) {
            Toast.makeText(getBaseContext(), taskResponse.getError(), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(RegisterUserActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    public PlaceAutoCompleteAdapter getLocationAdapter() {
        return locationAdapter;
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocationError() {
        _addressAutoCompleteText.setError(getResources().getString(R.string.error_address_sign_up));
    }

    public void removeLocationError() {
        _addressAutoCompleteText.setError(null);
    }

    public void onError() {
        _saveButton.setEnabled(true);
    }

    public void onSuccess() {
        _saveButton.setEnabled(false);
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void updateInterestValues(String category) {
        adapterValueType = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<>(interests.get(category)));

        adapterValueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spValue.setAdapter(adapterValueType);
        spValue.setEnabled(true);
    }

    public String getSelectedInterest() {
        return spValue.getSelectedItem().toString();
    }

    public String getInterestItem(int position) {
        return (String) interestListView.getItemAtPosition(position);
    }

    public void addInterest(String interest) {
        adapterListInterest.add(interest);
        setListViewHeightBasedOnChildren(interestListView);
    }

    public void removeInterest(String interest) {
        adapterListInterest.remove(interest);
        setListViewHeightBasedOnChildren(interestListView);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
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

    private void createLocationService(Activity mActivity) {
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .enableAutoManage((AppCompatActivity) mActivity, 0, (GoogleApiClient.OnConnectionFailedListener) mActivity)
                .addApi(Places.GEO_DATA_API)
                .build();

        _addressAutoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                controller.onLocationItemClicked(position);
            }
        });

        locationAdapter = new PlaceAutoCompleteAdapter(mActivity, android.R.layout.simple_list_item_1,
                mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);
        _addressAutoCompleteText.setAdapter(locationAdapter);
    }

    private void createInterestData(Activity mActivity) {
        //Getting interests
        interests = controller.getInterests();

        //Create list view of interest selected
        adapterListInterest = new ArrayAdapter<>(mActivity,
                android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<String>());
        interestListView.setAdapter(adapterListInterest);

        adapterCategoryType = new ArrayAdapter<>(mActivity,
                android.R.layout.simple_spinner_item, new ArrayList<>(interests.keySet()));

        adapterCategoryType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Create category spinner
        spCategory.setAdapter(adapterCategoryType);
        spCategory.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) mActivity);

        //Create value spinner
        spValue.setEnabled(false);

        interestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                controller.onInterestItemClicked(position);
            }
        });
    }

    private void showPhoto() {
        if (photo == null) {
            _addPhoto.setBackgroundResource(R.drawable.profile);
        } else {
            BitmapDrawable photoDrawable = new BitmapDrawable(getResources(), photo);
            _addPhoto.setBackground(photoDrawable);
        }
    }
}
