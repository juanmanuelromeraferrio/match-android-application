package com.match.client.entities;

import android.graphics.Bitmap;

import com.match.utils.PhotoUtils;

/**
 * Created by Juan Manuel Romera on 21/6/2016.
 */
public class Chat {

    private String id;
    private String name;
    private String age;
    private String photo;

    public Chat(String id, String name, String age, String photo) {
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
        if (photo != null)
            return PhotoUtils.base64ToBitmap(photo);
        else
            return null;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


