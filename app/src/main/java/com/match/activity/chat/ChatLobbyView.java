package com.match.activity.chat;

import com.match.activity.api.BaseView;
import com.match.client.entities.Chat;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 21/6/2016.
 */
public interface ChatLobbyView extends BaseView {

    void onError(String error);

    void addChats(List<Chat> chats);
}
