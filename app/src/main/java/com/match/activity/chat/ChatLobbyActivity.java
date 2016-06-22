package com.match.activity.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.match.R;
import com.match.client.entities.Chat;
import com.match.client.entities.User;
import com.match.service.factory.ServiceFactory;
import com.match.utils.UiUtils;

import java.util.List;
import java.util.Vector;


public class ChatLobbyActivity extends AppCompatActivity implements ChatLobbyView {

    private RecyclerView listView;
    private ChatAdapter listAdapter;
    private List<Chat> chatList;
    private TextView emptyView;
    private LinearLayout linearLayoutHeaderProgress;


    private ChatControllerLobby controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatList = new Vector<>();

        createGUI();
        controller = new ChatControllerLobbyImpl(this);
        controller.findChats();
    }

    private void initChat(Chat chat) {
        User localUser = ServiceFactory.getUserService().getLocalUser();
        Intent intent = new Intent(ChatLobbyActivity.this, ChatActivity.class);
        intent.putExtra("candidateName", chat.getName());
        intent.putExtra("idTo", chat.getId());
        intent.putExtra("idFrom", localUser.getId());
        intent.putExtra("photo", localUser.getPhoto());
        startActivity(intent);
    }

    private void createGUI() {
        setContentView(R.layout.activity_chat_lobby);

        // Set Title Chat
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChatLobby);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_chat_lobby));


        emptyView = (TextView) findViewById(R.id.empty_view);
        linearLayoutHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);

        listView = (RecyclerView) findViewById(R.id.listChatLobby);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setHasFixedSize(true);

        listAdapter = new ChatAdapter(this, chatList);
        listAdapter.setOnItemClickListener(new ChatAdapter.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(View itemView, int position) {
                                                   Chat chat = chatList.get(position);
                                                   initChat(chat);
                                               }
                                           }
        );

        listView.setAdapter(listAdapter);
    }


    @Override
    public void onError(String error) {

    }

    @Override
    public void addChats(List<Chat> chats) {
        if (!chats.isEmpty()) {
            this.chatList.addAll(chats);
            this.listView.setVisibility(View.VISIBLE);
            this.listAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void showProgress() {
        if (chatList.isEmpty()) {
            this.listView.setVisibility(View.GONE);
            this.emptyView.setVisibility(View.GONE);
            this.linearLayoutHeaderProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        this.linearLayoutHeaderProgress.setVisibility(View.GONE);
        if (this.chatList.isEmpty()) {
            this.listView.setVisibility(View.GONE);
            this.emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void goToNext() {

    }

    @Override
    public void sessionExpired() {
        UiUtils.showSessionExpired(this);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
