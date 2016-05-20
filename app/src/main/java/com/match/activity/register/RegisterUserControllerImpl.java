package com.match.activity.register;

import android.location.Geocoder;
import android.widget.AdapterView;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.match.client.entities.Location;
import com.match.infrastructure.PlaceAutoCompleteAdapter;
import com.match.service.api.UserService;
import com.match.service.factory.ServiceFactory;
import com.match.task.UpdateUserTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Juan Manuel Romera on 19/5/2016.
 */
public class RegisterUserControllerImpl implements RegisterUserController {

    private final RegisterUserActivity activity;

    public RegisterUserControllerImpl(RegisterUserActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onSaveUserButtonClicked() {
        if (!validate()) {
            activity.onError();
            return;
        } else {
            activity.onSuccess();
        }

        createUser();
    }

    @Override
    public void onSelectedCategoryItem(AdapterView<?> adapter, int position) {
        String category = adapter.getItemAtPosition(position).toString();
        activity.updateInterestValues(category);
    }

    @Override
    public void onAddInterestButtonClicked() {
        String interest = activity.getSelectedInterest();
        activity.addInterest(interest);
    }

    @Override
    public void onInterestItemClicked(int position) {
        String interest = activity.getInterestItem(position);
        activity.removeInterest(interest);
    }

    @Override
    public void onLocationItemClicked(int position) {
        final PlaceAutoCompleteAdapter.PlaceAutocomplete item = activity.getLocationAdapter().getItem(position);
        final String placeId = String.valueOf(item.placeId);
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                .getPlaceById(activity.getGoogleApiClient(), placeId);

        placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                if (!places.getStatus().isSuccess()) {
                    places.release();
                    activity.setLocation(null);
                    return;
                }
                final Place place = places.get(0);
                Geocoder gcd = new Geocoder(activity.getBaseContext(), Locale.getDefault());

                double longitude = place.getLatLng().longitude;
                double latitude = place.getLatLng().latitude;

                Location location = new Location(latitude, longitude);
                activity.setLocation(location);
                places.release();
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        Location location = activity.getLocation();
        if (location == null) {
            activity.setLocationError();
            valid = false;
        } else {
            activity.removeLocationError();
        }
        return valid;
    }

    private void createUser() {
        UserService userService = ServiceFactory.getUserService();
        UpdateUserTask task = new UpdateUserTask(userService, activity);
        task.execute(activity.getLocation(), activity.getPhoto());
    }


    public Map<String, List<String>> getInterests() {
        Map<String, List<String>> interests = new HashMap<>();
        List<String> bands = new ArrayList<String>();
        bands.add("The Beatles");
        bands.add("The Who");

        List<String> clubs = new ArrayList<String>();
        clubs.add("Independiente");
        clubs.add("Boca Juniors");

        interests.put("Bandas", bands);
        interests.put("Clubs", clubs);

        return interests;

    }
}
