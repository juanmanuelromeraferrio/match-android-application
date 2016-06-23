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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by pablo on 11/06/16.
 */
public abstract class UserMatchesService implements MatchService {

    protected Database database;
    protected ChatMapper mapper;

    protected UserMatchesService(Database database, ChatMapper mapper) {
        this.database = database;
        this.mapper = mapper;
    }

    public abstract List<Chat> findChats(User user) throws ServiceException;

    public abstract void acceptMatch(User user, Candidate candidate) throws ServiceException;

    protected List<Chat> filterChats(User user, List<Chat> chats) {
        List<Chat> localChats = user.getChats();
        if (localChats.isEmpty() || chats.isEmpty()) {
            return chats;
        }
        List<Chat> result = new Vector<>();
        Set<String> localChatsId = new HashSet<>();
        for (Chat chat : localChats) {
            localChatsId.add(chat.getId());
        }

        for (Chat chat : chats) {
            if (!localChatsId.contains(chat.getId())) {
                result.add(chat);
            }
        }
        return result;
    }

    @Override
    public ServiceType getType() {
        return ServiceType.USER_MATCHES;
    }
}
