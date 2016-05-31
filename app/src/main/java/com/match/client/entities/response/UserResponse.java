package com.match.client.entities.response;

import com.match.client.entities.Interest;
import com.match.client.entities.Location;

import java.util.List;
import java.util.Vector;

/**
 * Created by Juan Manuel Romera on 30/5/2016.
 */
public class UserResponse {

    private UserResponseContext user;

    public String getId() {
        return user.getId();
    }

    private class UserResponseContext {
        private String id;
        private String name;
        private String email;
        private String alias;
        private String sex;
        private List<Interest> interests;
        private Location location;
        private String photo;

        public UserResponseContext() {

        }

        public String getId() {
            return id;
        }

    }
}
