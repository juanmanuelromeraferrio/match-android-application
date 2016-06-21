package com.match.task.response;

import com.match.task.ChatTaskState;

/**
 * Created by pablo on 20/06/16.
 */
public class ChatTaskResponse extends TaskResponse {

    private ChatTaskState state;

    public ChatTaskResponse(ChatTaskState state){this.state = state;}

    public ChatTaskState getState() {
        return state;
    }

}
