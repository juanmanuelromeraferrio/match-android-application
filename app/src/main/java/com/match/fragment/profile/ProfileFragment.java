package com.match.fragment.profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.match.R;
import com.match.activity.HomeActivity;
import com.match.client.entities.User;
import com.match.service.factory.ServiceFactory;
import com.match.utils.PhotoUtils;
import com.match.utils.UiUtils;
import com.match.utils.Validator;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Juan Manuel Romera on 18/6/2016.
 */
public class ProfileFragment extends Fragment implements ProfileView {


    private static final int PICK_PHOTO_FOR_PROFILE = 100;

    private EditText _nameEditText;
    private EditText _ageEditText;
    private CircleImageView _addPhoto;
    private ProgressDialog progressDialog;
    private ProfileController controller;
    private Context activity;
    private Bitmap photo;
    private View mainView;
    private AppCompatButton updateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        controller = new ProfileControllerImpl(this);

        // View:
        mainView = inflater.inflate(R.layout.fragment_profile, container, false);
        createGUI(mainView);
        refreshLocalUserData();

        return mainView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_PROFILE && resultCode == Activity.RESULT_OK) {
            photo = PhotoUtils.decodeMediaImage(activity, data);
            showPhoto();
        }
    }

    private void createGUI(View mainView) {
        _nameEditText = (EditText) mainView.findViewById(R.id.input_name);
        _ageEditText = (EditText) mainView.findViewById(R.id.input_age);
        _addPhoto = (CircleImageView) mainView.findViewById(R.id.photoButton);
        _addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(v);
            }
        });

        updateButton = (AppCompatButton) mainView.findViewById(R.id.btn_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void refreshLocalUserData() {
        User localUser = ServiceFactory.getUserService().getLocalUser();
        //Set Name
        _nameEditText.setText(localUser.getName());
        //Set Age
        _ageEditText.setText(localUser.getAge());
        //Set Photo
        photo = PhotoUtils.base64ToBitmap(localUser.getPhoto());
        showPhoto();
    }

    private void showPhoto() {
        if (photo != null) {
            _addPhoto.setImageBitmap(photo);
        }
    }

    /* onClick button image */
    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_PROFILE);
    }

    private void updateProfile() {
        controller.updateUser(_nameEditText.getText().toString(), _ageEditText.getText().toString(), photo);
    }


    @Override
    public void setNameError() {
        TextView lbl = (TextView) mainView.findViewById(R.id.input_name);
        lbl.setError(activity.getResources().getString(R.string.error_user_name, Validator.MIN_LENGTH_USER_NAME));
    }

    @Override
    public void setPhotoError() {

    }

    @Override
    public void setAgeError() {
        TextView lbl = (TextView) mainView.findViewById(R.id.input_age);
        lbl.setError(getString(R.string.age_error));
    }

    @Override
    public void clearErrors() {
        TextView lblName = (TextView) mainView.findViewById(R.id.input_name);
        lblName.setError(null);

        TextView lblAge = (TextView) mainView.findViewById(R.id.input_age);
        lblAge.setError(null);
    }

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(activity, "", getResources().getString(R.string.updating_profile_info), true, false);
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
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void sessionExpired() {
        UiUtils.showSessionExpired(activity);
    }
}
