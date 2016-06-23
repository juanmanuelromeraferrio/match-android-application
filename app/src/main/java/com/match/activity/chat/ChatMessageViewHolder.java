package com.match.activity.chat;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.match.R;
import com.match.client.entities.ChatMessage;

/**
 * Created by Juan Manuel Romera on 22/6/2016.
 */
public class ChatMessageViewHolder extends RecyclerView.ViewHolder {

    private String localUserId;
    private TextView toChatMessage;
    private TextView fromChatMessage;

    public ChatMessageViewHolder(String localUserId, View itemView) {
        super(itemView);
        this.localUserId = localUserId;
        toChatMessage = (TextView) itemView.findViewById(R.id.toChatMessage);
        fromChatMessage = (TextView) itemView.findViewById(R.id.fromChatMessage);
    }

    public void loadMessage(ChatMessage chatMessage) {

        final boolean isMe = chatMessage.getUser() != null && chatMessage.getUser().equals(localUserId);
        if (isMe) {
            fromChatMessage.setText(chatMessage.getContent());
            fromChatMessage.setVisibility(View.VISIBLE);
            toChatMessage.setVisibility(View.INVISIBLE);
        } else {
            toChatMessage.setText(chatMessage.getContent());
            toChatMessage.setVisibility(View.VISIBLE);
            fromChatMessage.setVisibility(View.INVISIBLE);
        }
    }
}
