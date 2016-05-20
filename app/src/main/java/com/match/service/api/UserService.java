package com.match.service.api;

import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;

/**
 * Created by Juan Manuel Romera on 16/5/2016.
 * Service to establish the communication with
 * the user service in the server
 */
public abstract class UserService implements MatchService {

    protected Database database;

    protected UserService(Database database) {
        this.database = database;
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

    public abstract boolean isUserLogged(User user) throws ServiceException;

    public User getLocalUser() {
        return database.getUser();
    }

    public ServiceType getType() {
        return ServiceType.USER;
    }

    public boolean hasSavedInformation() {
        User user = database.getUser();
        return !user.getLocation().isDefault();
    }

    public void logout() {
        database.setUser(null);
        database.setToken(null);
    }
}
