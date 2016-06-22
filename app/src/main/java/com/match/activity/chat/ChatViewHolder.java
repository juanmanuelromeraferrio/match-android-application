package com.match.activity.chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.match.R;
import com.match.client.entities.Chat;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Juan Manuel Romera on 21/6/2016.
 */
public class ChatViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView photo;
    private TextView name;

    public ChatViewHolder(final View itemView, final ChatAdapter.OnItemClickListener listener) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.nameChat);
        photo = (CircleImageView) itemView.findViewById(R.id.photoChat);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onItemClick(itemView, getLayoutPosition());
            }
        });
    }

    public void loadChat(Chat chat) {
        name.setText(chat.getName());
        photo.setImageBitmap(chat.getPhoto());
    }
}
