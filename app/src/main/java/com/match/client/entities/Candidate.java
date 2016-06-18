package com.match.client.entities;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Juan Manuel Romera on 25/5/2016.
 */
public class Candidate implements Serializable{

    private String id;
    private String name;
    private String age;
    private Bitmap photo;
    private String interests;

    public Candidate(String id, String name, String age, Bitmap photo, String interests) {
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

    public Bitmap getPhoto() {
        return photo;
    }

    public String getInterests() {
        return interests;
    }

<<<<<<< HEAD
=======
    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getAge() {
        return age;
    }
>>>>>>> a7b31bd445dfa0883f1ec18b7d2c7f0087fcd181
}
