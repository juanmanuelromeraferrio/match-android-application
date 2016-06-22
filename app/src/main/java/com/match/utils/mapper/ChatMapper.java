package com.match.utils.mapper;

import com.match.client.entities.Chat;
import com.match.client.entities.User;

/**
 * Created by Juan Manuel Romera on 21/6/2016.
 */
public interface ChatMapper {

    Chat map(User user);
}
