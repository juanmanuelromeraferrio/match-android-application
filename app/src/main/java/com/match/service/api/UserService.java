package com.match.service.api;

import com.match.client.entities.User;
import com.match.error.service.ServiceException;

/**
 * Created by Juan Manuel Romera on 16/5/2016.
 * Service to establish the communication with
 * the user service in the server
 */
public abstract class UserService implements MatchService {

    /**
     * Create an user in the system
     * Returns the user's id.
     *s
     * @param user {@link User}
     * @return user's id {@link String}
     */
    public abstract void createUser(User user) throws ServiceException;

    public abstract void updateUser(User user);

    public ServiceType getType() {
        return ServiceType.USER;
    }
}
