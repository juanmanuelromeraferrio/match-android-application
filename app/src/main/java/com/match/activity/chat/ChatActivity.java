package com.match.activity.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.match.R;


/**
 * Created by pablo on 20/06/16.
 */
public class ChatActivity extends AppCompatActivity {

    private Button sendMessageButton;
    private EditText msgText;
    private ChatController controller;

    private String candidateName;
    private String idTo;
    private String idFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        candidateName = getIntent().getExtras().getString("candidateName");
        idTo = getIntent().getExtras().getString("idTo");
        idFrom = getIntent().getExtras().getString("idFrom");
        setContentView(R.layout.activity_chat);
        controller = new ChatControllerImpl(this);
        EditText matchName=(EditText)findViewById(R.id.matchName);
        msgText = (EditText)findViewById(R.id.msgText);
        sendMessageButton=(Button)findViewById(R.id.sendMessageButton);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        matchName.setText(candidateName);
    }

    private void sendMessage() {
        controller.sendMessage(idFrom,idTo,msgText.getText().toString());
    }

    public void clearMessage(){
        msgText.setText("");
    }

    public void disableSendButton() {
        sendMessageButton.setEnabled(false);
    }

    public void enableSendButton() {
        sendMessageButton.setEnabled(true);
    }
}
