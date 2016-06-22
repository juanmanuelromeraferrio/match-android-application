package com.match.service.api;

import com.match.client.entities.Candidate;
import com.match.client.entities.Chat;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;
import com.match.utils.mapper.CandidateMapper;
import com.match.utils.mapper.ChatMapper;
import com.match.utils.mapper.MatchChatMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablo on 11/06/16.
 */
public abstract class UserMatchesService implements MatchService{

    protected Database database;
    protected ChatMapper mapper;

    protected UserMatchesService(Database database, ChatMapper mapper) {
        this.database = database;
        this.mapper = mapper;
    }

    public abstract List<Chat> findChats(User user) throws ServiceException;

    public abstract void acceptMatch(User user, Candidate candidate) throws ServiceException;

    public boolean hasSavedInformation() {
        User user = database.getUser();
        return !user.getLocation().isDefault();
    }

    @Override
    public ServiceType getType() {
        return ServiceType.USER_MATCHES;
    }
}
