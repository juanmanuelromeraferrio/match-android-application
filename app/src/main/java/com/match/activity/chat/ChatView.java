package com.match.activity.chat;

import com.match.activity.api.BaseView;
import com.match.client.entities.ChatMessage;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 21/6/2016.
 */
public interface ChatView extends BaseView {

    void addMessages(Boolean newMessages, List<ChatMessage> messages);

    void onError(String error);

    void addMessage(ChatMessage chatSent);

    void clearTextBox();

    boolean isRunning();
}
