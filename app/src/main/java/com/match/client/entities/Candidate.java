package com.match.client.entities;

import android.graphics.Bitmap;

import com.match.utils.PhotoUtils;

import java.io.Serializable;

/**
 * Created by Juan Manuel Romera on 25/5/2016.
 */
public class Candidate implements Serializable {

    private String id;
    private String name;
    private String age;
    private String photo;
    private String interests;

    public Candidate(String id, String name, String age, String photo, String interests) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.photo = photo;
        this.interests = interests;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getInterests() {
        return interests;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAge() {
        return age;
    }

    @Override
    public String toString() {
        return getName();
    }

}
