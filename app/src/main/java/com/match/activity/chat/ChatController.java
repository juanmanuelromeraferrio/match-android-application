package com.match.activity.chat;

import com.match.activity.api.BaseController;
import com.match.client.entities.ChatMessage;

import java.util.List;

/**
 * Created by pablo on 20/06/16.
 */
public interface ChatController extends BaseController {

    void sendMessage(String msg);

    void pullHistory();

    void pullNewMessages();

    void setLastMessage(String idFrom, String idTo, String idMsg);

    List<ChatMessage> getMessages();

    void cancelPullNewMessages();

    void saveUser();
}
