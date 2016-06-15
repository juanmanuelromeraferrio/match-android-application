package com.match.client.entities.response;

/**
 * Created by Juan Manuel Romera on 10/6/2016.
 */
public class PhotoResponse {

    private String photo;

    public PhotoResponse(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
