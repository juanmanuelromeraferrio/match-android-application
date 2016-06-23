package com.match.activity.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.match.R;
import com.match.client.entities.ChatMessage;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 22/6/2016.
 */
public class ChatListAdapter extends ArrayAdapter<ChatMessage> {

    private String userId;

    public ChatListAdapter(Context context, String userId, List<ChatMessage> messages) {
        super(context, 0, messages);
        this.userId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.chat_message, parent, false);
        }

        ChatMessageViewHolder viewHolder = new ChatMessageViewHolder(userId, convertView);
        ChatMessage chatMessage = getItem(position);
        viewHolder.loadMessage(chatMessage);

        return convertView;
    }

}
