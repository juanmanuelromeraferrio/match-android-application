package com.match.activity.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.match.R;
import com.match.client.entities.Chat;
import com.match.fragment.ProgressViewHolder;
import com.match.fragment.candidates.CandidateViewHolder;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 22/5/2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM_PROGRESS = 1;
    private final int ITEM_CHAT = 2;

    private Context context;
    private List<Chat> chats;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ChatAdapter(Context context, List<Chat> chats) {
        this.context = context;
        this.chats = chats;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v;
        if (viewType == ITEM_CHAT) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat, parent, false);
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = windowManager.getDefaultDisplay().getWidth();
            v.setLayoutParams(new RecyclerView.LayoutParams(width, RecyclerView.LayoutParams.MATCH_PARENT));
            viewHolder = new ChatViewHolder(v, listener);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false);
            viewHolder = new ProgressViewHolder(v);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ChatViewHolder) {
            ChatViewHolder view = (ChatViewHolder) viewHolder;
            Chat chat = chats.get(position);
            view.loadChat(chat);
        } else {
            ProgressViewHolder view = (ProgressViewHolder) viewHolder;
            view.spin();
        }
    }

    @Override
    public int getItemCount() {
        return chats == null ? 0 : chats.size();
    }

    @Override
    public int getItemViewType(int position) {
        return chats.get(position) != null ? ITEM_CHAT : ITEM_PROGRESS;
    }
}
