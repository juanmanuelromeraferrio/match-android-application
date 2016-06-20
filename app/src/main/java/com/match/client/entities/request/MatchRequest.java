package com.match.client.entities.request;

/**
 * Created by pablo on 12/06/16.
 */
public class MatchRequest{

    private String idFrom;
    private String idTo;

    public MatchRequest(String idFrom,String idTo) {
        this.idFrom = idFrom;
        this.idTo = idTo;
    }

}
