package com.match.activity.chat;

import com.match.activity.api.BaseController;
import com.match.client.entities.Chat;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 21/6/2016.
 */
public interface ChatControllerLobby extends BaseController {

    void findChats();

    List<Chat> getChats();
}
