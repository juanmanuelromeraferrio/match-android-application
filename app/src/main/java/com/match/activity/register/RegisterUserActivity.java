package com.match.activity.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.match.R;
import com.match.activity.HomeActivity;
import com.match.client.entities.Interest;
import com.match.client.entities.Location;
import com.match.infrastructure.adpater.AutoCompleteGoogleApiAdapter;
import com.match.utils.PhotoUtils;
import com.match.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterUserActivity extends AppCompatActivity implements RegisterUserView, OnLocation, GoogleApiClient.OnConnectionFailedListener {

    private static final int PICK_PHOTO_FOR_PROFILE = 100;

    @InjectView(R.id.radioGroupUserSex)
    RadioGroup radioGroupUserSex;
    @InjectView(R.id.input_age)
    EditText _ageEditText;
    @InjectView(R.id.input_address)
    AutoCompleteTextView _addressAutoCompleteText;
    @InjectView(R.id.photoButton)
    CircleImageView _addPhoto;
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
    private ArrayAdapter<Interest> adapterListInterest;

    private AutoCompleteGoogleApiAdapter autoCompleteGoogleApiAdapter;

    private ProgressDialog progressDialog;
    private RegisterUserControllerImpl controller;

    private Bitmap photo;
    private Location location;
    private List<Interest> interests;

    private RegisterUserActivity _this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _this = this;
        controller = new RegisterUserControllerImpl(this);

        setContentView(R.layout.activity_register_user);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_sign_up_user_info));
        createLocationService();
        createInterestData();


    }

    /* onClick button image */
    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_PROFILE);
    }

    /* onClick add interest button */
    public void addInterest(View view) {
        String category = spCategory.getSelectedItem().toString();
        String value = spValue.getSelectedItem().toString();
        Interest interest = new Interest(category, value);
        if (!interests.contains(interest)) {
            interests.add(interest);
            adapterListInterest.notifyDataSetChanged();
            UiUtils.setListViewHeightBasedOnChildren(interestListView);
        }
    }

    /* onClick save button */
    public void saveUser(View view) {
        String sex = getSex();
        String age = _ageEditText.getText().toString();
        controller.saveUser(photo, sex, age, location, interests);
    }

    @NonNull
    private String getSex() {
        String sex = "";
        if (radioGroupUserSex.getCheckedRadioButtonId() != -1) {
            sex = ((RadioButton) findViewById(radioGroupUserSex.getCheckedRadioButtonId())).getText().toString();
        }
        return sex;
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
        finish();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,
                R.string.error_connection_google_api + " " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void setLocationError() {
        _addressAutoCompleteText.setError(getResources().getString(R.string.error_address_sign_up));
    }

    @Override
    public void setPhotoError() {

    }

    @Override
    public void setSexError() {
        TextView lblSex = (TextView) findViewById(R.id.lbSexo);
        lblSex.setError(getString(R.string.radio_group_error));
    }

    @Override
    public void setAgeError() {
        TextView lbl = (TextView) findViewById(R.id.input_age);
        lbl.setError(getString(R.string.age_error));
    }

    @Override
    public void clearErrors() {
        _addressAutoCompleteText.setError(null);

        TextView lblSex = (TextView) findViewById(R.id.lbSexo);
        lblSex.setError(null);

        TextView lblAge = (TextView) findViewById(R.id.input_age);
        lblAge.setError(null);
    }

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getBaseContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    private void createLocationService() {
        _addressAutoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCompleteGoogleApiAdapter.setLocation(_this, position);
            }
        });

        autoCompleteGoogleApiAdapter = new AutoCompleteGoogleApiAdapter(this);
        _addressAutoCompleteText.setAdapter(autoCompleteGoogleApiAdapter.build());
    }

    private void createInterestData() {
        //Create list view of interest selected
        interests = new ArrayList<>();
        adapterListInterest = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, interests);
        interestListView.setAdapter(adapterListInterest);

        adapterCategoryType = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<>(controller.getCategories()));

        adapterCategoryType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Create category spinner
        spCategory.setAdapter(adapterCategoryType);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                updateInterestValues(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Create value spinner
        spValue.setEnabled(false);

        interestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removeInterest(position);
            }
        });
    }

    private void removeInterest(int position) {
        Interest itemAtPosition = (Interest) interestListView.getItemAtPosition(position);
        interests.remove(itemAtPosition);
        adapterListInterest.notifyDataSetChanged();
        UiUtils.setListViewHeightBasedOnChildren(interestListView);
    }

    public void updateInterestValues(String category) {
        adapterValueType = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<>(controller.getInterestValues(category)));

        adapterValueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spValue.setAdapter(adapterValueType);
        spValue.setEnabled(true);
    }

    private void showPhoto() {
        if (photo == null) {
            _addPhoto.setBackgroundResource(R.drawable.profile);
        } else {
            _addPhoto.setImageBitmap(photo);
        }
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(RegisterUserActivity.this, "", getResources().getString(R.string.saving_user_info), true, false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void goToNext() {
        Intent intent = new Intent(RegisterUserActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void sessionExpired() {
        UiUtils.showSessionExpired(this);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
