package com.match.client.entities;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Juan Manuel Romera on 25/5/2016.
 */
public class Candidate implements Serializable{

    private String id;
    private String name;
    private Bitmap photo;
    private String interests;

    public Candidate(String id, String name, Bitmap photo, String interests) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.interests = interests;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public String getInterests() {
        return interests;
    }

}
