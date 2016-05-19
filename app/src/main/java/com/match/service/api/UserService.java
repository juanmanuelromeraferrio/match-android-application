package com.match.service.api;

import com.match.client.entities.Token;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.infrastructure.MatchDB;
import com.match.utils.Configuration;

/**
 * Created by Juan Manuel Romera on 16/5/2016.
 * Service to establish the communication with
 * the user service in the server
 */
public abstract class UserService implements MatchService {

    protected MatchDB db;

    protected UserService() {
        db = new MatchDB();
    }

    /**
     * Create an user in the system
     * Returns the user's id.
     * s
     *
     * @param user {@link User}
     * @return user's id {@link String}
     */
    public abstract void createUser(User user) throws ServiceException;

    public abstract void updateUser(User user) throws ServiceException;

    public User getLocalUser() {
        return db.getUser();
    }

    public ServiceType getType() {
        return ServiceType.USER;
    }

    public boolean isUserLogged() {
        Token token = db.getToken();
        if (token != null) {
            long createdAt = new Long(token.getCreatedAt()).longValue();
            long diff = System.currentTimeMillis() - createdAt;
            if (diff > Configuration.TOKEN_TIME_MS) {
                return false;
            }
            return true;
        }

        return false;
    }

    public boolean hasSavedInformation() {
        User user = db.getUser();
        return !user.getLocation().isDefault();
    }

    public void logout() {
        db.setUser(null);
        db.setToken(null);
    }
}
