package com.match.activity.chat;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.match.R;
import com.match.client.entities.ChatMessage;
import com.match.utils.UiUtils;

import java.util.List;


/**
 * Created by pablo on 20/06/16.
 */
public class ChatActivity extends AppCompatActivity implements ChatView {

    private FloatingActionButton sendMessageButton;
    private EditText msgText;
    private ChatController controller;
    private ChatListAdapter adapter;
    private List<ChatMessage> messages;
    private ListView listViewChat;
    private LinearLayout linearLayoutHeaderProgress;

    private String candidateName;
    private String idTo;
    private String idFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadChatParameters();
        controller = new ChatControllerImpl(this, idFrom, idTo);
        messages = controller.getMessages();
        createGUI();

        if (messages.isEmpty()) {
            controller.pullHistory();
        } else {
            scroolListViewToBottom();
        }
    }

    private void sendMessage() {
        if (!msgText.getText().toString().trim().isEmpty()) {
            controller.sendMessage(msgText.getText().toString());
        }
    }

    private void loadChatParameters() {
        this.candidateName = getIntent().getExtras().getString("candidateName");
        this.idTo = getIntent().getExtras().getString("idTo");
        this.idFrom = getIntent().getExtras().getString("idFrom");
    }

    private void createGUI() {
        setContentView(R.layout.activity_chat);
        // Set Title Chat
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(candidateName);

        this.linearLayoutHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        //Get Buttons
        this.msgText = (EditText) findViewById(R.id.msgText);
        this.sendMessageButton = (FloatingActionButton) findViewById(R.id.sendMessageButton);
        this.sendMessageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        this.listViewChat = (ListView) findViewById(R.id.listChat);
        this.listViewChat.setTranscriptMode(1);

        this.adapter = new ChatListAdapter(this, this.idFrom, this.messages);
        this.listViewChat.setAdapter(adapter);
    }

    private void scroolListViewToBottom() {
        listViewChat.post(new Runnable() {
            @Override
            public void run() {
                listViewChat.setSelection(adapter.getCount() - 1);
            }
        });
    }

    @Override
    public synchronized void addMessages(Boolean newMessages, List<ChatMessage> chatMessages) {

        // Si los mensajes son nuevos no agrego los mios
        // Esto lo deberia hacer el servidor
        boolean addMessages = false;
        for (ChatMessage chat : chatMessages) {
            if (!newMessages || chat.getUser().equals(idTo)) {
                addMessageAndSave(chat, Boolean.TRUE);
                addMessages = true;
            }
        }

        if (addMessages) {
            this.adapter.notifyDataSetChanged();
            ChatMessage lastMessage = this.messages.get(this.messages.size() - 1);
            controller.setLastMessage(idFrom, idTo, lastMessage.getId());
            scroolListViewToBottom();
        } else {
            controller.setLastMessage(idFrom, idTo, "");
        }
    }

    private void addMessageAndSave(ChatMessage chat, Boolean checkExists) {
        if (checkExists) {
            if (!this.messages.contains(chat)) {
                this.messages.add(chat);
                this.controller.saveUser();
            }
        } else {
            this.messages.add(chat);
            this.controller.saveUser();
        }

    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public synchronized void addMessage(ChatMessage chatSent) {
        addMessageAndSave(chatSent, Boolean.FALSE);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void clearTextBox() {
        msgText.setText("");
    }

    @Override
    public void showProgress() {
        if (messages.isEmpty()) {
            this.linearLayoutHeaderProgress.setVisibility(View.VISIBLE);
            this.listViewChat.setVisibility(View.GONE);
            this.msgText.setEnabled(Boolean.FALSE);
            this.sendMessageButton.setEnabled(Boolean.FALSE);
        }
    }

    @Override
    public void hideProgress() {
        this.linearLayoutHeaderProgress.setVisibility(View.GONE);
        this.listViewChat.setVisibility(View.VISIBLE);
        this.msgText.setEnabled(Boolean.TRUE);
        this.sendMessageButton.setEnabled(Boolean.TRUE);
    }


    @Override
    public void onStart() {
        super.onStart();
        controller.pullNewMessages();
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.cancelPullNewMessages();
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
