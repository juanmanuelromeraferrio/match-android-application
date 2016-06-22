package com.match.utils.mapper;

import com.match.client.entities.Chat;
import com.match.client.entities.User;
import com.match.utils.PhotoUtils;

/**
 * Created by Juan Manuel Romera on 21/6/2016.
 */
public class MatchChatMapper implements ChatMapper {

    @Override
    public Chat map(User user) {
        return new Chat(user.getId(), user.getName(), user.getAge(), PhotoUtils.base64ToBitmap(user.getPhoto()));
    }
}
