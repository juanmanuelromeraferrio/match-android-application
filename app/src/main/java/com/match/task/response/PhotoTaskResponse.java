package com.match.task.response;


import android.graphics.Bitmap;

/**
 * Created by Juan Manuel Romera on 11/6/2016.
 */
public class PhotoTaskResponse {

    private String idCandidatePhoto;
    private Bitmap photo;

    public PhotoTaskResponse(String idCandidatePhoto, Bitmap photo) {
        this.idCandidatePhoto = idCandidatePhoto;
        this.photo = photo;
    }

    public String getIdCandidatePhoto() {
        return idCandidatePhoto;
    }

    public Bitmap getPhoto() {
        return photo;
    }
}
