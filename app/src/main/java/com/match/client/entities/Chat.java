package com.match.client.entities;

import android.graphics.Bitmap;

/**
 * Created by Juan Manuel Romera on 21/6/2016.
 */
public class Chat {

    private String id;
    private String name;
    private String age;
    private Bitmap photo;

    public Chat(String id, String name, String age, Bitmap photo) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


