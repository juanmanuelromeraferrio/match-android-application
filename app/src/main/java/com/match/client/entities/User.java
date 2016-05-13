package com.match.client.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 * Created by Juan Manuel Romera on 12/5/2016.
 */
public class User implements Serializable{

    private String name;
    private String email;
    private String password;
    private List<Interest> interests;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.interests = new Vector<>();
    }
}
