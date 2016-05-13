package com.match.activity;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.match.R;
import com.match.client.entities.Location;
import com.match.client.entities.User;
import com.match.infrastructure.PlaceAutoCompleteAdapter;
import com.match.utils.Parameters;
import com.match.utils.WaitForInternet;
import com.match.utils.WaitForInternetCallback;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUpUserInfoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_address)
    AutoCompleteTextView _addressAutoCompleteText;
    @InjectView(R.id.btn_save)
    Button _saveButton;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutoCompleteAdapter mAdapter;

    private Location location;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WaitForInternetCallback callback = new WaitForInternetCallback(this) {
            public void onConnectionSuccess() {
                setContentView(R.layout.activity_sign_up_user_info);
                ButterKnife.inject(mActivity);

                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_sign_up_user_info));

                mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                        .enableAutoManage((AppCompatActivity) mActivity, 0, (GoogleApiClient.OnConnectionFailedListener) mActivity)
                        .addApi(Places.GEO_DATA_API)
                        .build();

                // Register a listener that receives callbacks when a suggestion has been selected
                _addressAutoCompleteText.setOnItemClickListener((AdapterView.OnItemClickListener) mActivity);

                // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
                // the entire world.
                mAdapter = new PlaceAutoCompleteAdapter(mActivity, android.R.layout.simple_list_item_1,
                        mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);
                _addressAutoCompleteText.setAdapter(mAdapter);

                _saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveUser();
                    }
                });
            }
        };
        WaitForInternet.setCallback(callback);
    }

    private void saveUser() {
        if (!validate()) {
            _saveButton.setEnabled(true);
            return;
        }

        _saveButton.setEnabled(false);

        User user = (User) getIntent().getSerializableExtra(Parameters.USER.toString());

        Intent intent = new Intent(SignUpUserInfoActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public boolean validate() {
        boolean valid = true;
        String errorDireccion = getResources().getString(R.string.error_address_sign_up);
        if (location == null) {
            _addressAutoCompleteText.setError(errorDireccion);
            valid = false;
        } else {
            _addressAutoCompleteText.setError(null);
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignUpUserInfoActivity.this, SignUpAccountInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a PlaceAutocomplete object from which we
             read the place ID.
              */
        final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
        final String placeId = String.valueOf(item.placeId);
            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                .getPlaceById(mGoogleApiClient, placeId);

        placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                if (!places.getStatus().isSuccess()) {
                    // Request did not complete successfully
                    places.release();
                    location = null;
                    return;
                }
                // Get the Place object from the buffer.
                final Place place = places.get(0);
                Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());

                double longitude = place.getLatLng().longitude;
                double latitude = place.getLatLng().latitude;

                location = new Location(latitude, longitude);
                places.release();
            }
        });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,
                R.string.error_connection_google_api + " " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }
}
