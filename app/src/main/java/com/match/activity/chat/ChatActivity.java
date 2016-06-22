package com.match.activity.chat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.match.R;


/**
 * Created by pablo on 20/06/16.
 */
public class ChatActivity extends AppCompatActivity {

    private FloatingActionButton sendMessageButton;
    private EditText msgText;
    private ChatController controller;

    private String candidateName;
    private String idTo;
    private String idFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new ChatControllerImpl(this);
        loadChatParameters();
        createGUI();
    }

    private void sendMessage() {
        controller.sendMessage(idFrom, idTo, msgText.getText().toString());
    }

    public void clearMessage() {
        msgText.setText("");
    }

    public void disableSendButton() {
        sendMessageButton.setEnabled(false);
    }

    public void enableSendButton() {
        sendMessageButton.setEnabled(true);
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
        //Get Buttons
        this.msgText = (EditText) findViewById(R.id.msgText);
        this.sendMessageButton = (FloatingActionButton) findViewById(R.id.sendMessageButton);
        this.sendMessageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }
}
