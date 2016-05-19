package com.match.client.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 * Created by Juan Manuel Romera on 12/5/2016.
 */
public class User implements Serializable {

    private String name;
    private String email;
    private String alias;
    private String password;
    private List<Interest> interests;
    private Location location;
    private String photo;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.alias = email;
        this.password = password;
        this.interests = new Vector<>();
        this.location = new Location();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAlias() {
        return alias;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public Location getLocation() {
        return location;
    }

    public String getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
