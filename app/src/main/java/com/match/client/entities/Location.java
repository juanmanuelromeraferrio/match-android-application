package com.match.client.entities;

import java.io.Serializable;

/**
 * Created by Juan Manuel Romera on 12/5/2016.
 */
public class Location implements Serializable {

    private String latitude;
    private String longitude;

    public Location() {
        this(0, 0);
    }

    public Location(double latitude, double longitude) {
        this.latitude = String.valueOf(latitude);
        this.longitude = String.valueOf(longitude);
    }

    public boolean isDefault() {
        double _lat = new Double(latitude).doubleValue();
        double _long = new Double(longitude).doubleValue();
        return _lat == 0 && _long == 0;
    }

}
