package com.match.client.entities.request;

import com.google.gson.annotations.SerializedName;
import com.match.client.entities.User;

import java.io.Serializable;

/**
 * Created by pablo on 12/06/16.
 */
public class MatchRequest{

    @SerializedName("idFrom")
    private String idFrom;
    @SerializedName("idTo")
    private String idTo;

    public MatchRequest(String idFrom,String idTo) {
        this.idFrom = idFrom;
        this.idTo = idTo;
    }

    public String getIdFrom() {
        return idFrom;
    }

    public String getIdTo() {
        return idTo;
    }

}
