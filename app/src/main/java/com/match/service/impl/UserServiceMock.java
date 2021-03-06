package com.match.service.impl;

import com.match.MatchApplication;
import com.match.R;
import com.match.client.entities.Token;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;
import com.match.service.api.UserService;
import com.match.utils.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juan Manuel Romera on 18/5/2016.
 */
public class UserServiceMock extends UserService {

    private Map<String, User> users = new HashMap<>();

    public UserServiceMock(Database database) {
        super(database);
    }

    @Override
    public void createUser(User user) throws ServiceException {
        if (userExists(user)) {
            String errorMessage = MatchApplication.getContext().getResources().getString(R.string.usuario_ya_registrado, user.getEmail());
            throw new ServiceException(errorMessage);
        }
        saveUser(user);
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        String userID = this.database.getUser().getId();
        if (users.containsKey(userID)) {
            saveUser(userID, user);
        } else {
            String errorMessage = MatchApplication.getContext().getResources().getString(R.string.usuario_no_registrado, user.getName());
            throw new ServiceException(errorMessage);
        }
    }

    @Override
    public boolean isUserLogged() throws ServiceException {
        return false;
    }

    @Override
    public void loginUser(String email, String password) throws ServiceException {
        User user = new User("Juan Manuel Romera Ferrio", email, password);
        saveUser(user);
    }

    private boolean userExists(User user) {
        for (User user_ : users.values()) {
            if (user_.getEmail().equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }


    public void saveUser(User user) {
        int size = users.size();
        size++;
        saveUser(String.valueOf(size), user);
    }

    private void saveUser(String userID, User user) {
        user.setId(userID);
        users.put(userID, user);
        database.setUser(user);
    }
}
