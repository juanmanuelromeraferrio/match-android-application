package com.match.task.response;


import android.graphics.Bitmap;

/**
 * Created by Juan Manuel Romera on 11/6/2016.
 */
public class PhotoTaskResponse {

    private String idCandidatePhoto;
    private String photo;

    public PhotoTaskResponse(String idCandidatePhoto, String photo) {
        this.idCandidatePhoto = idCandidatePhoto;
        this.photo = photo;
    }

    public String getIdCandidatePhoto() {
        return idCandidatePhoto;
    }

    public String getPhoto() {
        return photo;
    }
}
