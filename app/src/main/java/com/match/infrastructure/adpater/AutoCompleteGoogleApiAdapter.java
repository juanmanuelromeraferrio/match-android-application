package com.match.infrastructure.adpater;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.match.activity.register.OnLocation;
import com.match.client.entities.Location;

/**
 * Created by Juan Manuel Romera on 21/5/2016.
 */
public class AutoCompleteGoogleApiAdapter {

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));


    private Context context;
    private GoogleApiClient mGoogleApiClient;
    private PlaceAutoCompleteAdapter adapter;

    public AutoCompleteGoogleApiAdapter(Context context) {
        this.context = context;
        this.mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((AppCompatActivity) context, 0, (GoogleApiClient.OnConnectionFailedListener) context)
                .addApi(Places.GEO_DATA_API)
                .build();

    }

    public PlaceAutoCompleteAdapter build() {
        adapter = new PlaceAutoCompleteAdapter(context, android.R.layout.simple_list_item_1,
                mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);
        return adapter;
    }

    public void setLocation(final OnLocation onLocation, int position) {
        final PlaceAutoCompleteAdapter.PlaceAutocomplete item = adapter.getItem(position);
        final String placeId = String.valueOf(item.placeId);
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                .getPlaceById(this.mGoogleApiClient, placeId);

        placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                if (!places.getStatus().isSuccess()) {
                    places.release();
                    onLocation.setLocation(null);
                    return;
                }
                final Place place = places.get(0);

                double longitude = place.getLatLng().longitude;
                double latitude = place.getLatLng().latitude;

                Location location = new Location(latitude, longitude);
                onLocation.setLocation(location);
                places.release();
            }
        });
    }
}
