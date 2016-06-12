package com.match.client.entities;

import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;

import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

/**
 * Created by Juan Manuel Romera on 25/5/2016.
 */
public class Candidate implements Parcelable{

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

    public Candidate(Parcel in) {
        readFromParcel(in);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(bitMapToString(photo));
        parcel.writeString(interests);
    }

    private String bitMapToString(Bitmap bitmap){
       /* ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        Base64.encodeToString(b, Base64.DEFAULT);*/
        String temp="";
        return temp;
    }

    private Bitmap stringToBitMap(String encodedString){
        try {
           // byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=null;//BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void readFromParcel(Parcel in) {
        id = in.readString();
        name = in.readString();
        photo = null;//stringToBitMap(in.readString());
    }

    public static final Parcelable.Creator<Candidate> CREATOR = new Parcelable.Creator<Candidate>() {
        public Candidate createFromParcel(Parcel in) {
            return new Candidate(in);
        }

        public Candidate[] newArray(int size) {
            return new Candidate[size];
        }
    };

}
