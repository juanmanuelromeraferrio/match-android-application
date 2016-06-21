package com.match.activity.chat;

import com.match.activity.api.BaseController;

/**
 * Created by pablo on 20/06/16.
 */
public interface ChatController extends BaseController {

    void sendMessage(String idFrom, String idUTo,String msg);

}
